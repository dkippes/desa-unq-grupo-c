package ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.CryptoCurrencyNotFoundException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@ExtendWith(MockitoExtension::class)
class BinanceProxyServiceTest {
    @InjectMocks
    private lateinit var binanceProxyService: BinanceProxyService

    @Mock
    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setUp() {
        binanceProxyService = BinanceProxyService()
        binanceProxyService.binanceApiURL = "https://api.binance.com/api/v3/"
        binanceProxyService.restTemplate = restTemplate
    }

    @Test
    fun `should return list of CryptoCurrency when API returns valid response getAllCryptoCurrencyValues`() {
        // Arrange
        val mockResponse = """
            [
                {"symbol": "BTCUSDT", "price": "50000.0"}
            ]
        """
        val url = "https://api.binance.com/api/v3/ticker/price?symbols=[\"ALICEUSDT\",\"MATICUSDT\",\"AXSUSDT\",\"AAVEUSDT\",\"ATOMUSDT\",\"NEOUSDT\",\"DOTUSDT\",\"ETHUSDT\",\"CAKEUSDT\",\"BTCUSDT\",\"BNBUSDT\",\"ADAUSDT\",\"TRXUSDT\",\"AUDIOUSDT\"]"
        `when`(restTemplate.getForObject(eq(url), eq(String::class.java))).thenReturn(mockResponse)

        // Act
        val result = binanceProxyService.getAllCryptoCurrencyValues()

        // Assert
        assertNotNull(result)
        assertEquals(1, result?.size)
        assertEquals("BTCUSDT", (result?.get(0) as CryptoCurrency).symbol.toString())
        assertEquals(BigDecimal("50000.0"), (result[0] as CryptoCurrency).price)
    }

    @Test
    fun `should return list of CryptoCurrency when API returns valid response for getCryptoCurrencyValue`() {
        // Arrange
        val url = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT"
        val expectedCrypto = CryptoCurrency(SYMBOL.BTCUSDT, BigDecimal("50000.0"), LocalDateTime.now())
        `when`(restTemplate.getForObject(eq(url), eq(CryptoCurrency::class.java))).thenReturn(expectedCrypto)

        // Act
        val result = binanceProxyService.getCryptoCurrencyValue(SYMBOL.BTCUSDT)

        // Assert
        assertNotNull(result)
        assertEquals("BTCUSDT", result?.symbol.toString())
        assertEquals(BigDecimal("50000.0"), result?.price)
    }

    @Test
    fun `should return a list of CurrentCryptoQuotePrice when API returns valid response for getHourlyQuotes`() {
        // Arrange
        val symbol = SYMBOL.BTCUSDT
        val mockResponse: Array<Array<Any>> = arrayOf(
            arrayOf(1622548800000L, "35000.0"),  // Make sure to use Long for timestamp
            arrayOf(1622549100000L, "35100.0")
        )
        val url = "${binanceProxyService.binanceApiURL}klines?symbol=${symbol}&interval=5m&limit=280"
        `when`(restTemplate.getForEntity(eq(url), eq(Array<Array<Any>>::class.java)))
            .thenReturn(ResponseEntity.ok(mockResponse))

        // Act
        val result = binanceProxyService.getHourlyQuotes(symbol)

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(1622548800000), ZoneId.systemDefault()), result[0].dateTime)
        assertEquals(BigDecimal("35000.0"), BigDecimal(result[0].price))
        assertEquals(LocalDateTime.ofInstant(Instant.ofEpochMilli(1622549100000), ZoneId.systemDefault()), result[1].dateTime)
        assertEquals(BigDecimal("35100.0"), BigDecimal(result[1].price))
    }

    @Test
    fun `should throw CryptoCurrencyNotFoundException when API response is null for getHourlyQuotes`() {
        // Arrange
        val symbol = SYMBOL.BTCUSDT
        val url = "${binanceProxyService.binanceApiURL}klines?symbol=${symbol}&interval=5m&limit=280"
        `when`(restTemplate.getForEntity(eq(url), eq(Array<Array<Any>>::class.java))).thenReturn(ResponseEntity.ok(null))

        // Act & Assert
        assertThrows<CryptoCurrencyNotFoundException> {
            binanceProxyService.getHourlyQuotes(symbol)
        }
    }
}