package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Transaction
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.AccountRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.IntentRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.TransactionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.*
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrElse


@Service
class TransactionServiceImpl : TransactionService {
    @Autowired private lateinit var transactionRepository: TransactionRepository
    @Autowired private lateinit var accountRepository: AccountRepository
    @Autowired private lateinit var intentRepository: IntentRepository
    @Autowired private lateinit var modelMapper: ObjectMapper

    override fun getVolumeBetweenDates(from: LocalDate, to: LocalDate): ResponseVolumeDTO {
        val sumResult = transactionRepository.findAllByStatusAndInitiatedAtBetween(
            TransactionStatus.TRANSFER_RECEIVE,
            from.atStartOfDay(), to.atTime(23, 59)
        )
        val cryptos = transactionRepository.findCryptosByStatusAndInitiatedAtBetween(TransactionStatus.TRANSFER_RECEIVE,
            from.atStartOfDay(), to.atTime(23, 59)).map {
                CryptoStockDTO(
                    SYMBOL.valueOfIndex((it[0] as Byte).toInt()).name,
                    it[1] as Double,
                    it[2] as Double,
                    it[3] as Double
                )
        }.toList()


        val totalOperated = (sumResult[0][0] as BigDecimal).toDouble()
        val localTotalOperated = (sumResult[0][1] as BigDecimal).toDouble()
        return ResponseVolumeDTO(
                totalOperated = totalOperated,
                localTotalOperated = localTotalOperated,
                cryptos=cryptos
        )

    }

    override fun processTransaction(accountId: Long, transactionId: Long, action: TransactionStatus): ResponseTransactionDTO {
        val transaction = transactionRepository.findById(transactionId).getOrElse { throw EntityNotFoundException("Transaction not found") }
        val account = accountRepository.findById(accountId).getOrElse { throw EntityNotFoundException("Account not found") }
        
        //Chequear Crypto value.
        when (action) {
            TransactionStatus.TRANSFER_SENT -> {
                account.sendTransfer(transaction, false)
            }

            TransactionStatus.TRANSFER_RECEIVE -> {
                account.confirmReception(transaction, false)
            }

            TransactionStatus.CANCELED -> {
                account.cancel(transaction)
            }

            else -> {
                throw IllegalArgumentException("Invalid action")
            }
        }

        transactionRepository.save(transaction)

        return ResponseTransactionDTO(
            id = transaction.id!!,
            price = transaction.intention!!.nominalPrice,
            amount = transaction.intention!!.nominalQuantity,
            fullName = account.user!!.getFullName(),
            timesOperated = account.transactions.size,
            reputation = account.user!!.getOperationsReputations(),
            address = transaction.getAddress(),
            action = transaction.status.name
        )
    }

    override fun generateTransaction(accountId: Long, operationId: Long): ResponseTransactionDTO {
        val account = accountRepository.findById(accountId).getOrElse { throw EntityNotFoundException("Account not found") }
        val operation = intentRepository.findById(operationId).getOrElse { throw EntityNotFoundException("Operation not found") }

        val transaction = operation.generateNewTransaction(
            account
        )
        transactionRepository.save(transaction)

        return ResponseTransactionDTO(
            id = transaction.id!!,
            price = transaction.intention!!.nominalPrice,
            amount = transaction.intention!!.nominalQuantity,
            fullName = account.user!!.getFullName(),
            timesOperated = account.transactions.size,
            //Esto deberia ser un metodo de la cuenta!!! TODO
            reputation = account.user!!.getOperationsReputations(),
            address = transaction.getAddress(),
            action = transaction.status.name
        )
    }
}