package ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.LocalDateTime

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
}