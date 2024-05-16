@file:Suppress("SENSELESS_COMPARISON")

package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.AccountRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.IntentRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.TransactionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.*
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.jvm.optionals.getOrElse


@Service
class TransactionServiceImpl : TransactionService {
    @Autowired private lateinit var transactionRepository: TransactionRepository
    @Autowired private lateinit var accountRepository: AccountRepository
    @Autowired private lateinit var intentRepository: IntentRepository
    @Autowired private lateinit var cryptoService: CryptoService

    override fun getVolumeBetweenDates(userId: Long, from: LocalDate, to: LocalDate): ResponseVolumeDTO {
        val sumResult = transactionRepository.findAllByUserAndStatusAndInitiatedAtBetween(
            userId,
            TransactionStatus.TRANSFER_RECEIVE,
            from.atStartOfDay(), to.atTime(23, 59)
        )

        // Renombrar seller y buyer a ownerUser and interestedUser para ser mas abstractos
        // y evitar problemas

        if(sumResult[0][0] == null) {
            return ResponseVolumeDTO(
                totalOperated = 0.0,
                localTotalOperated = 0.0,
                cryptos = emptyList()
            )
        }

        val cryptos = transactionRepository.findAllCryptoByUserAndStatusAndInitiatedAtBetween(
            userId,
            TransactionStatus.TRANSFER_RECEIVE,
            from.atStartOfDay(), to.atTime(23, 59)).map {
            CryptoStockDTO(
                symbol = SYMBOL.valueOfIndex((it[0] as Byte).toInt()).name,
                price = (it[1] as BigDecimal).toDouble(),
                quantity = (it[2] as BigDecimal).toDouble(),
                localPrice = (it[3] as BigDecimal).toDouble()
            )
        }.toList()


        val totalOperated = sumResult.firstOrNull()?.let {
            (it[0] as BigDecimal).toDouble()
        } ?: 0.0

        val localTotalOperated = sumResult.firstOrNull()?.let {
            (it[1] as BigDecimal).toDouble()
        } ?: 0.0

        return ResponseVolumeDTO(
                totalOperated = totalOperated,
                localTotalOperated = localTotalOperated,
                cryptos=cryptos
        )

    }

    override fun processTransaction(accountId: Long, transactionId: Long, action: TransactionStatus): ResponseTransactionDTO {
        val transaction = transactionRepository.findById(transactionId).getOrElse { throw EntityNotFoundException("Transaction not found") }
        val account = accountRepository.findById(accountId).getOrElse { throw EntityNotFoundException("Account not found") }


        when (action) {
            TransactionStatus.TRANSFER_SENT -> {
                account.sendTransfer(transaction)
            }

            TransactionStatus.TRANSFER_RECEIVE -> {
                account.confirmReception(transaction)
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
        val currentPrice = cryptoService.getCryptoCurrencyPrice(operation.symbol)?.price!!.toDouble()

        val transaction = operation.generateNewTransaction(
            account,
            currentPrice
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