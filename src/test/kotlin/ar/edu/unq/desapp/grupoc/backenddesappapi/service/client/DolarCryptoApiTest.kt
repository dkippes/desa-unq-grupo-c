package ar.edu.unq.desapp.grupoc.backenddesappapi.service.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

class DolarCryptoApiTest {

    private lateinit var dolarCryptoApi: DolarCryptoApi
    private lateinit var restTemplate: RestTemplate
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        dolarCryptoApi = DolarCryptoApi()
        restTemplate = Mockito.mock(RestTemplate::class.java)
        objectMapper = ObjectMapper().registerModule(KotlinModule())
        dolarCryptoApi.restTemplate = restTemplate
        dolarCryptoApi.dolarApiUrl = "https://dolarapi.com/v1/dolares/cripto"
        dolarCryptoApi.objectMapper = objectMapper
    }

    @Test
    fun `should return DolarCrypto when API returns valid response`() {
        // Arrange
        val mockResponse = """{"compra": 150.0, "venta": 160.0}"""
        val url = "https://dolarapi.com/v1/dolares/cripto"
        Mockito.`when`(restTemplate.getForObject(url, String::class.java))
            .thenReturn(mockResponse)

        // Act
        val result = dolarCryptoApi.getDolarCrypto()

        // Assert
        val expected = DolarCrypto(BigDecimal("150.0"), BigDecimal("160.0"))
        assertEquals(expected.sell, result.sell)
        assertEquals(expected.buy, result.buy)
    }

    // Define the DolarCrypto data class here
    data class DolarCrypto(val value: Double)
}