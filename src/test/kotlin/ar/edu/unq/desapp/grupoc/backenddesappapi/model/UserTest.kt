package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class UserTest {
    private lateinit var user : User

    @BeforeEach
    fun setup() {
        user = User(
            name = "Jose",
            email = "jose@gmail.com",
            lastName = "Del ñoca",
            password = "Se!23456",
            address = "Wilde 2034",
        )
    }

    @Test
    fun assertUserData() {
        assertAll({
            assertEquals(user.name, "Jose");
            assertEquals(user.email, "jose@gmail.com");
            assertEquals(user.lastName, "Del ñoca");
            assertEquals(user.password, "Se!23456");
            assertEquals(user.address, "Wilde 2034");
        })
    }

    @Test
    fun assertEquality() {
        val userCopy = User(
            name = "Jose",
            email = "jose@gmail.com",
            lastName = "Del ñoca",
            password = "Se!23456",
            address = "Wilde 2034",
        )
        val otherUser = User(
            name = "Marcos",
            email = "marcos@gmail.com",
            lastName = "Martinez",
            password = "Ro?64251",
            address = "Bernal 2034",
        )

        assertAll({
            assertEquals(this.user,userCopy);
            assertEquals(this.user.hashCode(), userCopy.hashCode());
            assertNotEquals(this.user, otherUser);
            assertNotEquals(this.user.hashCode(), otherUser.hashCode())
        })

    }

    @Test
    fun `hashCode should return same value for equal objects`() {
        val user1 = User("John", "Doe", "john@example.com", "password", "Address 123")
        val user2 = User("John", "Doe", "john@example.com", "password", "Address 123")

        assertEquals(user1.hashCode(), user2.hashCode())
    }
}