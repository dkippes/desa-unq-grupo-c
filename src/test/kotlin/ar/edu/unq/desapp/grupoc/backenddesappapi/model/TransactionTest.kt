package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
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

    @Test
    fun `test transaction id should be null after instantiation`() {
        val transaction = Transaction()

        assertNull(transaction.id)
    }

    @Test
    fun `test getAddress when operation is buy`() {
        val transaction = Transaction()
        val seller = Account(walletAddress = "53546676", cvu = "1111111111111111111112"  )
        val buyer = Account(walletAddress = "12345678", cvu = "1111111111111111111111"  )
        transaction.buyer = buyer
        transaction.seller = seller
        transaction.intention = OperationIntent(
            nominalQuantity = BigDecimal(100.0),
            nominalPrice = BigDecimal(10.0),
            localPrice = BigDecimal(12.0),
            operation = OPERATION.BUY,
            account = buyer,
            symbol = SYMBOL.ADAUSDT,
            status = OperationStatus.OPEN
        )

        assertEquals("12345678", transaction.getAddress())
    }

    @Test
    fun `test getAddress when operation is sell`() {
        val transaction = Transaction()
        val seller = Account(
            walletAddress
            = "53546676", cvu = "1111111111111111111112"
        )
        val buyer = Account(
            walletAddress
            = "12345678", cvu = "1111111111111111111111"
        )
        transaction.buyer = buyer
        transaction.seller = seller
        transaction.intention = OperationIntent(
            nominalQuantity = BigDecimal(100.0),
            nominalPrice = BigDecimal(10.0),
            localPrice = BigDecimal(12.0),
            operation = OPERATION.SELL,
            account = seller,
            symbol = SYMBOL.ADAUSDT,
            status = OperationStatus.OPEN
        )

        assertEquals("1111111111111111111112", transaction.getAddress())
    }
}