package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.utils.JwtUtil
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.UserServiceImpl
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestLoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestRegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseAccountDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseUserDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User as SpringUser
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

    @MockBean
    private lateinit var userService: UserServiceImpl

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    @MockBean
    private lateinit var jwtUtil: JwtUtil


    @Test
    fun shouldRegisterAUser() {
        val userData = RequestRegisterUserDTO(
            name = "Jose",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)

        val authenticationToken = UsernamePasswordAuthenticationToken(userData.email, userData.password)
        val authenticatedUser = SpringUser("juan@gmail.com", "123456sD!", emptyList())
        val authentication: Authentication = UsernamePasswordAuthenticationToken(authenticatedUser, null, emptyList())


        Mockito.`when`(userService.registerUser(userData)).thenReturn(ResponseUserDTO(
            id = 1L,
            name = "Jose",
            email = "juan@gmail.com",
            address = "Wilde 12",
            account = ResponseAccountDTO(
                cvu = "1111111111111111111111",
                walletAddress = "12345678",
                reputation = 0,
                id = 1L
            ),
            lastName = "Marces"
        ))
        Mockito.`when`(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication)
        Mockito.`when`(jwtUtil.generateToken(userData.email!!)).thenReturn("mocked-jwt-token")

        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth/register")
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
            .andExpect(MockMvcResultMatchers.header().string("Authorization", "Bearer mocked-jwt-token"))

    }

    @Test
    fun shouldFailWhenUserRequestDTOisCreated() {
        val userData = RequestRegisterUserDTO(
            name = "",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)

        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(parsedUserData)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun shouldFailWhenLoginUserDoesNotExist() {
        val userData = RequestLoginUserDTO(
            email = "email@notexist.com",
            password = "123456sD!"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)
        Mockito.`when`(userService.login(userData)).thenThrow(
            UserNotFoundException())
        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(parsedUserData)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun shouldLoginUserSuccessfully() {
        val userData = RequestLoginUserDTO(
            email = "juan@gmail.com",
            password = "123456sD!"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)

        val authenticationToken = UsernamePasswordAuthenticationToken(userData.email, userData.password)
        val authenticatedUser = SpringUser("juan@gmail.com", "123456sD!", emptyList())
        val authentication: Authentication = UsernamePasswordAuthenticationToken(authenticatedUser, null, emptyList())


        Mockito.`when`(userService.login(userData)).thenReturn(ResponseUserDTO(
            id = 1L,
            name = "Jose",
            email = "juan@gmail.com",
            address = "Wilde 12",
            account = ResponseAccountDTO(
                cvu = "1111111111111111111111",
                walletAddress = "12345678",
                reputation = 0,
                id = 1L
            ),
            lastName = "Marces"
        ))
        Mockito.`when`(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication)
        Mockito.`when`(jwtUtil.generateToken(userData.email!!)).thenReturn("mocked-jwt-token")

        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(parsedUserData)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.header().string("Authorization", "Bearer mocked-jwt-token"))
    }

    @Test
    fun shouldFailWhenLoginUserWithInvalidCredentials() {
        val userData = RequestLoginUserDTO(
            email = "juan@gmail.com",
            password = "invalid_password"
        )
        val parsedUserData = ObjectMapper().writeValueAsString(userData)

        mockMvc.perform(MockMvcRequestBuilders.post("/users/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(parsedUserData)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}