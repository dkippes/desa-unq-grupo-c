package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.*
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.IntentRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.TransactionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import org.apache.coyote.BadRequestException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class TransactionServiceImplTest {
    @InjectMocks
    private lateinit var transactionService: TransactionServiceImpl
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var intentRepository: IntentRepository
    @Mock
    private lateinit var transactionRepository: TransactionRepository
    @Mock
    private lateinit var cryptoService: CryptoService


    @Test
    fun `processTransaction should throw UserNotFoundException when user is not found`() {
        val userId = 1L
        val transactionId = 1L
        val action = TransactionStatus.TRANSFER_SENT

        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        assertThrows<UserNotFoundException>("User not found") {
            transactionService.processTransaction(
                userId,
                transactionId,
                action
            )
        }
    }

    @Test
    fun `processTransaction should update transaction status`() {
        val userId = 1L
        val transactionId = 1L
        val action = TransactionStatus.TRANSFER_SENT

        val user = User(
            email = "TEST@GMAIL.COM",
            password = "P@55word!",
            name = "TEST",
            lastName = "TEST",
            address = "holamundo!",
            account = Account(
                walletAddress = "walletAddress",
                cvu = "cvu",
            ).let { it.id = 1L; it}
        ).let {
            it.id = userId
            it
        }
        val operation = OperationIntent(
        nominalPrice = BigDecimal("1.0"),
        nominalQuantity = BigDecimal("1.0"),
        localPrice = BigDecimal("1.0"),
        operation = OPERATION.BUY,
        symbol = SYMBOL.BTCUSDT,
        ).let { it.id = 1L; it}

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        val transaction = Transaction(
            status = TransactionStatus.WAITING_ACTION,
            intention = operation,
            initiatedAt = LocalDateTime.now(),
            buyer = Account(
                walletAddress = "walletAddress",
                cvu = "cvu",
            ).let { it.id = 1L; it},
            seller = Account(
                walletAddress = "2alletAddress",
                cvu = "2vu",
            ).let { it.id = 2L; it}
        ).let{ it.id = transactionId; it}
        `when`(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction))

        val responseTransactionDTO = transactionService.processTransaction(userId, transactionId, action)
        Assertions.assertEquals(action.name, transaction.status.name)
    }

    @Test
    fun `generateTransaction should throw UserNotFoundException when user is not found`() {
        // Given
        val userId = 1L
        val operationId = 1L
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        // When & Then
        assertThrows<UserNotFoundException>{
            transactionService.generateTransaction(userId, operationId)
        }
    }

    @Test
    fun `generateTransaction should throw BadRequestException when transaction is already generated`() {
        // Given
        val userId = 1L
        val operationId = 1L
        val user = User(
            email = "TEST@GMAIL.COM",
            password = "P@55word!",
            name = "TEST",
            lastName = "TEST",
            address = "holamundo!",
            account = Account(
                walletAddress = "walletAddress",
                cvu = "cvu",
            ).let { it.id = 1L; it}
        ).let {
            it.id = userId
            it
        }

        val operation = OperationIntent(
            nominalPrice = BigDecimal("1.0"),
            nominalQuantity = BigDecimal("1.0"),
            localPrice = BigDecimal("1.0"),
            operation = OPERATION.BUY,
            symbol = SYMBOL.BTCUSDT,
            transaction = Transaction(
                buyer = Account(
                    walletAddress = "walletAddress",
                    cvu = "cvu",
                ).let { it.id = 1L; it},
                seller = Account(
                    walletAddress = "2alletAddress",
                    cvu = "2vu",
                ).let { it.id = 2L; it},
                status = TransactionStatus.TRANSFER_SENT,
                initiatedAt = LocalDateTime.now(),
            )
        ).let { it.id = operationId; it}
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(intentRepository.findById(operationId)).thenReturn(Optional.of(operation))
        `when`(cryptoService.getCryptoCurrencyPrice(SYMBOL.BTCUSDT)).thenReturn(CryptoCurrency(price= BigDecimal("1.0"),symbol=SYMBOL.BTCUSDT, lastUpdateDateAndTime = LocalDateTime.now()))
        assertThrows<BadRequestException>{
            transactionService.generateTransaction(userId, operationId)
        }
    }

    @Test
    fun `generateTransaction should create a new transaction`() {
        // Given
        val userId = 1L
        val operationId = 1L
        val user =
            User(
                email = "csrfdsfds@exmaple.com",
                password = "P@55word!",
                name = "TEST",
                lastName = "TEST",
                address = "holamundo!",
                account = Account(
                    walletAddress = "walletAddress",
                    cvu = "cvu",
                ).let { it.id = 1L; it}
            ).let {
                it.id = userId
                it
            }
        val operation =
            OperationIntent(
                nominalPrice = BigDecimal("100.1"),
                nominalQuantity = BigDecimal("1.0"),
                localPrice = BigDecimal("1.0"),
                operation = OPERATION.BUY,
                symbol = SYMBOL.BTCUSDT,
            ).let { it.id = operationId; it}

        val currentPrice = BigDecimal("100.0")
        val transaction =
            Transaction(
                buyer = Account(
                    walletAddress = "walletAddress",
                    cvu = "cvu",
                ).let { it.id = 1L; it},
                seller = Account(
                    walletAddress = "2alletAddress",
                    cvu = "2vu",
                ).let { it.id = 2L; it},
                status = TransactionStatus.TRANSFER_SENT,
                intention = operation,
                initiatedAt = LocalDateTime.now(),
            )
        Mockito.`when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        Mockito.`when`(intentRepository.findById(operationId )).thenReturn(Optional.of(operation))
        Mockito.`when`(cryptoService.getCryptoCurrencyPrice(SYMBOL.BTCUSDT)).thenReturn(CryptoCurrency(price=currentPrice,symbol=SYMBOL.BTCUSDT, lastUpdateDateAndTime = LocalDateTime.now()))
        Mockito.`when`(transactionRepository.save(any())).thenReturn(transaction.let { it.id = 1L; it})
        val responseTransactionDTO = transactionService.generateTransaction(userId, operationId)

        Assertions.assertEquals(transaction.id, responseTransactionDTO.id)
        Assertions.assertEquals(transaction.intention!!.nominalPrice, responseTransactionDTO.price)
        Assertions.assertEquals(transaction.intention!!.nominalQuantity, responseTransactionDTO.amount)
        Assertions.assertEquals(user.getFullName(), responseTransactionDTO.fullName)
        Assertions.assertEquals(user.account!!.getTimesOperated(), responseTransactionDTO.timesOperated)
        Assertions.assertEquals(user.account!!.getOperationsReputations(), responseTransactionDTO.reputation)
        Assertions.assertEquals(transaction.getAddress(), responseTransactionDTO.address)
        Assertions.assertEquals(transaction.status.name, responseTransactionDTO.action)
    }

    @Test
    fun `getVolumeBetweenDates should throw UserNotFoundException when user is not found`() {
        // Given
        val userId = 1L
        val from = LocalDate.now().minusDays(1)
        val to = LocalDate.now()
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        // When & Then
        assertThrows<UserNotFoundException> {
            transactionService.getVolumeBetweenDates(userId, from, to)
        }
    }


    @Test
    fun `getVolumeBetweenDates should return ResponseVolumeDTO with correct values`() {
        // Given
        val userId = 1L
        val from = LocalDate.now().minusDays(1)
        val to = LocalDate.now()
        val user = User(
            email = "TEST@GMAIL.COM",
            password = "P@55word!",
            name = "TEST",
            lastName = "TEST",
            address = "holamundo!",
            account = Account(
                walletAddress = "walletAddress",
                cvu = "cvu",
            ).apply { id = 1L }
        ).apply { id = userId }

        val volumeData: List<Array<Any>> = listOf(
            arrayOf(BigDecimal("100.0"), BigDecimal("120.0"))
        )
        val cryptoData: List<Array<Any>> = listOf(
            arrayOf(1.toByte(), BigDecimal("50000.0"), BigDecimal("2.0"), BigDecimal("100000.0"))
        )

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(
            transactionRepository.findAllByUserAndStatusAndInitiatedAtBetween(
                user.account!!.id!!,
                TransactionStatus.TRANSFER_RECEIVE,
                from.atStartOfDay(), to.atTime(23, 59)
            )
        ).thenReturn(volumeData)
        `when`(
            transactionRepository.findAllCryptoByUserAndStatusAndInitiatedAtBetween(
                user.account!!.id!!,
                TransactionStatus.TRANSFER_RECEIVE,
                from.atStartOfDay(), to.atTime(23, 59)
            )
        ).thenReturn(cryptoData)

        // When
        val response = transactionService.getVolumeBetweenDates(userId, from, to)

        // Then
        Assertions.assertEquals(BigDecimal("100.00"), response.totalOperated)
        Assertions.assertEquals(BigDecimal("120.00"), response.localTotalOperated)
        Assertions.assertEquals(1, response.operatedCryptos.size)
        val cryptoStock = response.operatedCryptos.first()
        Assertions.assertEquals(SYMBOL.MATICUSDT, SYMBOL.valueOfIndex(1))
        Assertions.assertEquals(BigDecimal("50000.00"), cryptoStock.price)
        Assertions.assertEquals(2.0, cryptoStock.quantity)
        Assertions.assertEquals(BigDecimal("100000.00"), cryptoStock.localPrice)
    }
}
