package ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto.CryptoCurrencyDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class BinanceProxyServiceTest {
    @InjectMocks
    private lateinit var binanceProxyService: BinanceProxyService

    @Mock
    private lateinit var restTemplate: RestTemplate

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        binanceProxyService = BinanceProxyService()
        binanceProxyService.binanceApiURL = "https://api.binance.com/api/v3/"
        binanceProxyService.restTemplate = restTemplate
        binanceProxyService.objectMapper = objectMapper
    }

    @Test
    fun `should return list of CryptoCurrency when API returns valid response`() {
        // Arrange
        val mockResponse = """
            [
                {"symbol": "BTCUSDT", "price": "50000.0"},
                {"symbol": "ETHUSDT", "price": "4000.0"}
            ]
        """
        val url = "https://api.binance.com/api/v3/ticker/price?symbols=[\"ALICEUSDT\",\"MATICUSDT\",\"AXSUSDT\",\"AAVEUSDT\",\"ATOMUSDT\",\"NEOUSDT\",\"DOTUSDT\",\"ETHUSDT\",\"CAKEUSDT\",\"BTCUSDT\",\"BNBUSDT\",\"ADAUSDT\",\"TRXUSDT\",\"AUDIOUSDT\"]"
        `when`(restTemplate.getForObject(eq(url), eq(String::class.java))).thenReturn(mockResponse)

        val cryptoCurrencyDTOs = listOf(
            CryptoCurrencyDTO(SYMBOL.BTCUSDT, BigDecimal("50000.0")),
            CryptoCurrencyDTO(SYMBOL.ETHUSDT, BigDecimal("4000.0"))
        )
        `when`(objectMapper.readValue<List<CryptoCurrencyDTO>>(mockResponse)).thenReturn(cryptoCurrencyDTOs)

        // Act
        val result = binanceProxyService.getAllCryptoCurrencyValues()

        // Assert
        assertNotNull(result)
        assertEquals(2, result?.size)
        assertEquals("BTCUSDT", (result?.get(0) as CryptoCurrency).symbol.toString())
        assertEquals(BigDecimal("50000.0"), (result?.get(0) as CryptoCurrency).price)
        assertEquals("ETHUSDT", (result[1] as CryptoCurrency).symbol.toString())
        assertEquals(BigDecimal("4000.0"), (result[1] as CryptoCurrency).price)
    }
}