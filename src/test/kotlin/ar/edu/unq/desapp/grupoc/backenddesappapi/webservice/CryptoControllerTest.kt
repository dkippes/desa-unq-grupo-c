package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.JacksonConfig
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.CryptoService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(JacksonConfig::class)
class CryptoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var cryptoService: CryptoService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun shouldReturnAllCryptoCurrencies() {
        mockMvc.perform(MockMvcRequestBuilders.get("/crypto/currencies"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun shouldReturnCryptoCurrencyBySymbol() {
        val symbol = SYMBOL.BNBUSDT

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/crypto/currency/$symbol"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
        val jsonResponse = result.response.contentAsString

        val symbolPresent = JsonPath.read<String>(jsonResponse, "$.symbol")

        Assertions.assertEquals("BNBUSDT", symbolPresent)
    }
}
