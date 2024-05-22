package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Account
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.OperationIntent
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.OperationIntentionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.DolarCrypto
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.DolarCryptoApi
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase.BuyDollarStrategy
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase.DollarStrategyProvider
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys.BinanceProxyService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseActiveIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseListCryptoActiveIntentionDTO
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class IntentServiceImplTest {
    @InjectMocks
    private lateinit var intentService: IntentServiceImpl
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var operationIntentRepository: OperationIntentionRepository
    @Mock
    private lateinit var dolarCryptoApi: DolarCryptoApi
    @Mock
    private lateinit var binanceProxyService: BinanceProxyService
    @Mock
    private lateinit var dolarStrategyProvider: DollarStrategyProvider


    @Test
    fun `should throw UserNotFoundException when user does not exist`() {
        // Arrange
        val userId = 1L
        val intent = RequestExpressIntentionDTO(cryptoAsset = SYMBOL.BTCUSDT, nominalAmount = BigDecimal("0.1"), operationType = OPERATION.BUY)
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows(UserNotFoundException::class.java) {
            intentService.expressIntention(intent, userId)
        }
    }

    @Test
    fun `should return ExpressIntentionResponseDTO when user exists in expressIntention`() {
        // Arrange
        val userId = 1L
        val user = User(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            password = "password123",
            address = "123 Main St",
            account = null
        )
        user.id = userId
        val intent = RequestExpressIntentionDTO(cryptoAsset = SYMBOL.BTCUSDT, nominalAmount = BigDecimal("0.1"), operationType = OPERATION.BUY)
        val dolarCrypto = DolarCrypto(BigDecimal("150.0"), BigDecimal("160.0"))
        val cryptoValue = CryptoCurrency(SYMBOL.BTCUSDT, BigDecimal("5000.0"), LocalDateTime.now())
        val dollarValue = BigDecimal("155.0")
        val localQuote = BigDecimal("7750.0")
        val cryptoQuote = BigDecimal("500.0")
        val operationIntention = OperationIntent(
            symbol = SYMBOL.BTCUSDT,
            nominalQuantity = BigDecimal("0.1"),
            nominalPrice = cryptoQuote,
            localPrice = localQuote,
            operation = OPERATION.BUY,
            account = null,
            status = OperationStatus.OPEN,
            transaction = null,
        )

        val savedOperationIntent = operationIntention.apply { id = 1L }
        val expectedResponse = ResponseExpressIntentionDTO(
            id = 1L,
            cryptoAsset = intent.cryptoAsset,
            nominalAmount = intent.nominalAmount,
            cryptoQuote = BigDecimal("500.0"),
            operationAmountARS = localQuote,
            firstName = user.name,
            lastName = user.lastName,
            operationType = intent.operationType
        )

        val buyDollarStrategy = mock(BuyDollarStrategy::class.java)

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(dolarCryptoApi.getDolarCrypto()).thenReturn(dolarCrypto)
        `when`(binanceProxyService.getCryptoCurrencyValue(intent.cryptoAsset!!)).thenReturn(cryptoValue)
        `when`(dolarStrategyProvider.getStrategy(intent.operationType!!)).thenReturn(buyDollarStrategy)
        `when`(buyDollarStrategy.getDollarValue(dolarCrypto)).thenReturn(dollarValue)
        `when`(operationIntentRepository.save(any(OperationIntent::class.java))).thenReturn(savedOperationIntent)

        // Act
        val result = intentService.expressIntention(intent, userId)

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `should throw UserNotFoundException when user does not exist in listActiveIntentionResponseDTO`() {
        // Arrange
        val userId = 1L
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows(UserNotFoundException::class.java) {
            intentService.listActiveIntentionResponseDTO(userId)
        }
    }

    @Test
    fun `should return ListCryotoActiveIntentionResponseDTO when user exists in listActiveIntentionResponseDTO`() {
        // Arrange
        val userId = 1L
        val account = Account(cvu = "123456789", walletAddress = "0x123456789")
        account.id = 1L
        val user = User(
            name = "John",
            lastName = "Doe",
            email = "john.doe@example.com",
            password = "password123",
            address = "123 Main St",
            account = account
        )
        user.id = userId

        val operationIntention1 = OperationIntent(
            symbol = SYMBOL.BTCUSDT,
            nominalQuantity = BigDecimal("0.1"),
            nominalPrice = BigDecimal("500.0"),
            localPrice = BigDecimal("7750.0"),
            operation = OPERATION.BUY,
            account = account,
            status = OperationStatus.OPEN,
            transaction = null
        )
        operationIntention1.id = 1L

        val activeIntents = listOf(operationIntention1)
        account.intents = mutableListOf(operationIntention1)

        val expectedResponse = ResponseListCryptoActiveIntentionDTO(
            firstName = user.name,
            lastName = user.lastName,
            reputation = "0",
            listActiveIntention = activeIntents.map {
                ResponseActiveIntentionDTO(
                    id = it.id!!,
                    symbol = it.symbol,
                    nominalAmount = it.nominalQuantity,
                    localAmount = it.localPrice,
                    cryptoQuote = it.nominalPrice,
                    createdDate = it.createdDate
                )
            }
        )

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(operationIntentRepository.findByAccountIdAndStatus(user.id!!, OperationStatus.OPEN)).thenReturn(activeIntents)

        // Act
        val result = intentService.listActiveIntentionResponseDTO(userId)

        // Assert
        assertEquals(expectedResponse, result)
    }
}