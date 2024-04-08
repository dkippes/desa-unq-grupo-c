package ar.edu.unq.desapp.grupoc.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserRepositoryImplTest {

    @InjectMocks
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Test
    fun `test registerUser`() {
        val user = User(
            name = "Jose",
            password = "123456sD!",
            email = "juan@gmail.com",
            address = "Wilde 12",
            walletAddress = "12345678",
            cvu = "1111111111111111111111",
            lastName = "Marces"
        )

        val registeredUser = userRepositoryImpl.registerUser(user)

        assertEquals(user, registeredUser)
    }
}
