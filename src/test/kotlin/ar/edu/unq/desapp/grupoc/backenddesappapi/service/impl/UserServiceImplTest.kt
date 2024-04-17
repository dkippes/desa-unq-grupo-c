package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Account
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserServiceImpl

    @Test
    fun `test registerUser`() {
        val userData = RegisterUserDTO(
            name = "Jose",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )

        val user = User(
            name = "Jose",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            lastName = "Marces",
            account = Account(
                cvu = "1111111111111111111111",
                walletAddress = "12345678"
            )
        )

        Mockito.`when`(userRepository.registerUser(user)).thenReturn(user)

        val registeredUser = userService.registerUser(userData)

        verify(userRepository).registerUser(user)

        assertEquals(user, registeredUser)
    }
}