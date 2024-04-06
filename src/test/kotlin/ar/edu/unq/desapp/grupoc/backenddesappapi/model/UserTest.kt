package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class UserTest {
    private lateinit var user : User

    @BeforeEach
    fun setup() {
        user = User(
            name = "Jose",
            cvu = "0123456789012345678912",
            email = "jose@gmail.com",
            lastName = "Del ñoca",
            password = "Se!23456",
            address = "Wilde 2034",
            walletAddress = "12345678"
        )
    }

    @Test
    fun assertUserData() {
        assertAll({
            Assertions.assertEquals(user.name, "Jose");
            Assertions.assertEquals(user.cvu, "0123456789012345678912");
            Assertions.assertEquals(user.email, "jose@gmail.com");
            Assertions.assertEquals(user.lastName, "Del ñoca");
            Assertions.assertEquals(user.password, "Se!23456");
            Assertions.assertEquals(user.address, "Wilde 2034");
            Assertions.assertEquals(user.walletAddress, "12345678")
        })
    }
}