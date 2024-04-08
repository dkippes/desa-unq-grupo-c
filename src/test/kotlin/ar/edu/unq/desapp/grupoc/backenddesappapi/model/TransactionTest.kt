package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TransactionTest {

    @Test
    fun `test getPointsForFinish when less than or equal to 30 minutes`() {
        val transaction = Transaction(initiatedAt = LocalDateTime.now().minusMinutes(15))

        assertEquals(10, transaction.getPointsForFinish())
    }

    @Test
    fun `test getPointsForFinish when more than 30 minutes`() {
        val transaction = Transaction(initiatedAt = LocalDateTime.now().minusMinutes(45))

        assertEquals(5, transaction.getPointsForFinish())
    }

    @Test
    fun `test getPointsPenalizationForCancel`() {
        val transaction = Transaction()

        assertEquals(20, transaction.getPointsPenalizationForCancel())
    }
}