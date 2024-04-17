package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldRegisterAUser() {
        val userData = RegisterUserDTO(
            name = "Jose",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(parsedUserData)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jose"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("juan@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Wilde 12"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.account.cvu").value("1111111111111111111111"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.account.walletAddress").value("12345678"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Marces"))
    }

    @Test
    fun shouldFailWhenUserRequestDTOisCreated() {
        val userData = RegisterUserDTO(
            name = "",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)

        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(parsedUserData)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}