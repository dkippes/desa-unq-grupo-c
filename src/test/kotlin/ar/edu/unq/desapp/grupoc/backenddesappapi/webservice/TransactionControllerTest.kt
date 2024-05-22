package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.JacksonConfig
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestCreateTransactionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestProcessDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestVolumeDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseTransactionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseVolumeDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(JacksonConfig::class)
class TransactionControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var transactionService: TransactionService

    @Test
    fun `process transaction - success`() {
        val transactionId = 1L
        val userId = 100L
        val action = "CONFIRM"
        val requestDto = RequestProcessDTO(TransactionStatus.TRANSFER_SENT, userId)
        val responseDto = ResponseTransactionDTO(userId, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, "full",
            10, "10", "adress", "CONFIRMED")

        `when`(transactionService.processTransaction(userId, transactionId, TransactionStatus.TRANSFER_RECEIVE))
            .thenReturn(responseDto)

        mockMvc.perform(put("/transaction/$transactionId/process")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk())
    }

    @Test
    fun `generate transaction - success`() {
        val userId = 100L
        val operationId = 50L
        val requestDto = RequestCreateTransactionDTO(userId, operationId)
        val responseDto = ResponseTransactionDTO(userId, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, "full",
            10, "10", "adress", "CONFIRMED")

        `when`(transactionService.generateTransaction(userId, operationId)).thenReturn(responseDto)

        mockMvc.perform(post("/transaction/generate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk())
    }

    @Test
    fun `inform user volume - success`() {
        val userId = 100L
        val from = LocalDate.now()
        val to = LocalDate.now()
        val requestDto = RequestVolumeDTO(from, to)
        val responseDto = ResponseVolumeDTO(LocalDateTime.now(), BigDecimal.ZERO, BigDecimal.ZERO, emptyList())

        `when`(transactionService.getVolumeBetweenDates(userId, from, to)).thenReturn(responseDto)

        mockMvc.perform(post("/transaction/volume/$userId")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestDto.toJson())
        ).andExpect(status().isOk())
    }

}