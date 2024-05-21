package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserAlreadyExistsException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.LoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import org.springframework.boot.test.context.SpringBootTest

@ExtendWith(MockitoExtension::class)
@SpringBootTest
class UserServiceImplTest {
    @Autowired
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

        val registeredUser = userService.registerUser(userData)

        assertAll({
            assertEquals(userData.name, registeredUser.name);
            assertEquals(userData.lastName, registeredUser.lastName);
            assertEquals(userData.email, registeredUser.email);
            assertEquals(userData.address, registeredUser.address);
            assertEquals(userData.walletAddress, registeredUser.account.walletAddress);
            assertEquals(userData.cvu, registeredUser.account.cvu);

        })
        assertNotNull(registeredUser.id)
    }

    @Test
    fun `registerUser should throw UserAlreadyExistsException when user already exists`() {
        val registerUserDTO = RegisterUserDTO(
            name = "Jose",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )


        assertThrows<UserAlreadyExistsException>{
            userService.registerUser(registerUserDTO)
        }
    }

    @Test
    fun `login should return user when credentials are correct`() {
        userService.registerUser(
            RegisterUserDTO(
                name = "Jose",
                password = "123456sD!",
                email = "test@example.com",
                address = "Wilde 12",
                walletAddress = "12345678",
                cvu = "1111111111111111111111",
                lastName = "Marces"
            )
        )
        val loginUserDTO = LoginUserDTO(email = "test@example.com", password = "123456sD!")

        val responseUserDTO = userService.login(loginUserDTO)

        assertNotNull(responseUserDTO)
        assertEquals("test@example.com", responseUserDTO.email)
    }

    @Test
    fun `login should throw UserNotFoundException when user is not found`() {
        val loginUserDTO = LoginUserDTO(email = "notfound@example.com", password = "password")


        assertThrows<UserNotFoundException> {
            userService.login(loginUserDTO)
        }
    }

}