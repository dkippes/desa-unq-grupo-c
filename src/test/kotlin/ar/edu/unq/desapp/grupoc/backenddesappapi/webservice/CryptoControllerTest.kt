package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.JacksonConfig
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.CryptoService
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(JacksonConfig::class)
class CryptoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var cryptoService: CryptoService

    @Test
    fun shouldReturnAllCryptoCurrencies() {
        val emptyList  = CryptoCurrencyList(emptyList())
        Mockito.`when`(cryptoService.getAllCryptoCurrencyPrices())
            .thenReturn(emptyList)

        mockMvc.perform(MockMvcRequestBuilders.get("/crypto/currencies"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun shouldReturnCryptoCurrencyBySymbol() {
        val symbol = SYMBOL.BNBUSDT
        val crypto  = CryptoCurrency(symbol, BigDecimal.TEN, LocalDateTime.now())
        Mockito.`when`(cryptoService.getCryptoCurrencyPrice(symbol))
            .thenReturn(crypto)

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/crypto/currency/$symbol"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        val jsonResponse = result.response.contentAsString

        val symbolPresent = JsonPath.read<String>(jsonResponse, "$.symbol")

        Assertions.assertEquals("BNBUSDT", symbolPresent)
    }
}
