package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
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
}