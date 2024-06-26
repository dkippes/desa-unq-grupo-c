package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.JacksonConfig
import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.utils.JwtUtil
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.IntentService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseActiveIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseListCryptoActiveIntentionDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(JacksonConfig::class)
@WithMockUser(username = "testuser", roles = ["USER"])
class IntentControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var intentService: IntentService

    @Test
    fun `express intent - success`() {

        val userId = 1L
        val userInput = RequestExpressIntentionDTO(
            cryptoAsset = SYMBOL.BTCUSDT,
            nominalAmount = BigDecimal("2.0"),
            operationType = OPERATION.BUY
        )
        val responseDTO = ResponseExpressIntentionDTO(
            id = 1L,
            cryptoAsset = SYMBOL.BTCUSDT,
            nominalAmount = BigDecimal("2.0"),
            cryptoQuote = BigDecimal("50000.0"),
            operationAmountARS = BigDecimal("100000.0"),
            firstName = "John",
            lastName = "Doe",
            operationType = OPERATION.BUY
        )

        `when`(intentService.expressIntention(userInput, userId))
            .thenReturn(responseDTO)

        mockMvc.perform(post("/intent/express/$userId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(userInput)))
            .andExpect(status().isOk())
    }

    @Test
    fun `listActiveIntentionResponseDTO - success`() {
        val userId = 1L
        val activeIntentionResponseDTO = ResponseActiveIntentionDTO(
            id = 1L,
            createdDate = LocalDateTime.now(),
            symbol = SYMBOL.BTCUSDT,
            nominalAmount = BigDecimal("2.0"),
            cryptoQuote = BigDecimal("50000.0"),
            localAmount = BigDecimal("100000.0")
        )
        val responseDTO = ResponseListCryptoActiveIntentionDTO(
            firstName = "John",
            lastName = "Doe",
            reputation = "10",
            listActiveIntention = listOf(activeIntentionResponseDTO)
        )

        `when`(intentService.listActiveIntentionResponseDTO(userId))
            .thenReturn(responseDTO)

        mockMvc.perform(get("/intent/list-active/$userId")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
    }
}