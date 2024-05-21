@file:Suppress("SENSELESS_COMPARISON")

package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.IntentRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.TransactionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase.QuoteCalculator
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.*
import jakarta.persistence.EntityNotFoundException
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.BadRequest
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.jvm.optionals.getOrElse


@Service
class TransactionServiceImpl : TransactionService {
    @Autowired private lateinit var transactionRepository: TransactionRepository
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var intentRepository: IntentRepository
    @Autowired private lateinit var cryptoService: CryptoService

    override fun getVolumeBetweenDates(userId: Long, from: LocalDate, to: LocalDate): ResponseVolumeDTO {
        val user = userRepository.findById(userId).getOrElse { throw UserNotFoundException() }
        val volume = transactionRepository.findAllByUserAndStatusAndInitiatedAtBetween(
            user.account!!.id!!,
            TransactionStatus.TRANSFER_RECEIVE,
            from.atStartOfDay(), to.atTime(23, 59)
        )
        val cryptos = transactionRepository.findAllCryptoByUserAndStatusAndInitiatedAtBetween(
            user.account!!.id!!,
            TransactionStatus.TRANSFER_RECEIVE,
            from.atStartOfDay(), to.atTime(23, 59))

        return mapToResponseVolumeDTO(volume, cryptos)
    }

    override fun processTransaction(userId: Long, transactionId: Long, action: TransactionStatus): ResponseTransactionDTO {
        val user = userRepository.findById(userId).getOrElse { throw UserNotFoundException() }
        val transaction = transactionRepository.findById(transactionId).getOrElse { throw EntityNotFoundException("Transaction not found") }

        when (action) {
            TransactionStatus.TRANSFER_SENT -> {
                user.account!!.sendTransfer(transaction)
            }

            TransactionStatus.TRANSFER_RECEIVE -> {
                user.account!!.confirmReception(transaction)
            }

            TransactionStatus.CANCELED -> {
                user.account!!.cancel(transaction)
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
            fullName = user.getFullName(),
            timesOperated = user.account!!.getTimesOperated(),
            reputation = user.account!!.getOperationsReputations(),
            address = transaction.getAddress(),
            action = transaction.status.name
        )
    }

    override fun generateTransaction(userId: Long, operationId: Long): ResponseTransactionDTO {
        val user = userRepository.findById(userId).getOrElse { throw UserNotFoundException()}
        val operation = intentRepository.findById(operationId).getOrElse { throw EntityNotFoundException("Operation not found") }
        val currentPrice = cryptoService.getCryptoCurrencyPrice(operation.symbol)?.price!!.toDouble()

        if(operation.transaction != null) {
            throw BadRequestException("Transaction already generated.")
        }



        val transaction = transactionRepository.save(operation.generateNewTransaction(
            user.account!!,
            currentPrice
        ))

        return ResponseTransactionDTO(
            id = transaction.id!!,
            price = transaction.intention!!.nominalPrice,
            amount = transaction.intention!!.nominalQuantity,
            fullName = user!!.getFullName(),
            timesOperated = user.account!!.getTimesOperated(),
            reputation = user.account!!.getOperationsReputations(),
            address = transaction.getAddress(),
            action = transaction.status.name
        )
    }

    private fun mapToCryptoStock(crypto: Array<Any>): CryptoStockDTO {
        return CryptoStockDTO(
            symbol = SYMBOL.valueOfIndex((crypto[0] as Byte).toInt()).name,
            price = QuoteCalculator.roundQuote(crypto[1] as BigDecimal),
            quantity = (crypto[2] as BigDecimal).toDouble(),
            localPrice = QuoteCalculator.roundQuote(crypto[3] as BigDecimal)
        )
    }
    private fun mapToResponseVolumeDTO(volume: List<Array<Any>>, cryptos: List<Array<Any>>): ResponseVolumeDTO {
        if(volume[0][0] == null) {
            return ResponseVolumeDTO(
                totalOperated = BigDecimal("0.0"),
                localTotalOperated = BigDecimal("0.0"),
                operatedCryptos = emptyList()
            )
        }

        val totalOperated = volume.firstOrNull()?.let {
            QuoteCalculator.roundQuote(it[0] as BigDecimal)
        } ?: BigDecimal("0.0")

        val localTotalOperated = volume.firstOrNull()?.let {
           QuoteCalculator.roundQuote(it[1] as BigDecimal)
        } ?: BigDecimal("0.0")

        val cryptosStocks = cryptos.map {
            mapToCryptoStock(it)
        }.toList()

        return ResponseVolumeDTO(
          localTotalOperated = localTotalOperated,
            totalOperated = totalOperated,
            operatedCryptos = cryptosStocks
        )
    }

}