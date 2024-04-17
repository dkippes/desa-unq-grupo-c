package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class OperationIntentTest() {
    @Test
    fun shouldBeActiveWhenStatusIsOpen() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = 100.0,
            nominalPrice = 10.0,
            localPrice = 12.0,
            operation = OPERATION.BUY
        )
        Assertions.assertTrue(operationIntent.isActive())
    }

    @Test
    fun shouldNotBeActiveWhenStatusIsClosed() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = 100.0,
            nominalPrice = 10.0,
            localPrice = 12.0,
            operation = OPERATION.BUY,
            status = OperationStatus.CLOSED
        )
        Assertions.assertFalse(operationIntent.isActive())
    }

    @Test
    fun shouldGenerateASellTransactionWhenUsersAreValid() {
        val user1 = mock(Account::class.java)
        val user2 = mock(Account::class.java)
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = 100.0,
            nominalPrice = 10.0,
            localPrice = 12.0,
            operation = OPERATION.SELL,
        )

        operationIntent.generateNewTransaction(user2)

        Assertions.assertNotNull(operationIntent.transaction)
        Assertions.assertEquals(operationIntent.transaction!!.status, TransactionStatus.WAITING_ACTION)
        Assertions.assertEquals(user1, operationIntent.transaction?.seller)
        Assertions.assertEquals(user2, operationIntent.transaction?.buyer)
    }

    @Test
    fun shouldGenerateABuyTransactionWhenUsersAreValid() {
        val user1 = mock(Account::class.java)
        val user2 = mock(Account::class.java)
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = 100.0,
            nominalPrice = 10.0,
            localPrice = 12.0,
            operation = OPERATION.BUY,
            account = user1
        )

        operationIntent.generateNewTransaction(user2)

        Assertions.assertNotNull(operationIntent.transaction)
        Assertions.assertEquals(operationIntent.transaction!!.status, TransactionStatus.WAITING_ACTION)
        Assertions.assertEquals(user2, operationIntent.transaction?.seller)
        Assertions.assertEquals(user1, operationIntent.transaction?.buyer)
    }

    @Test
    fun `test operation intent id should be null after instantiation`() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = 100.0,
            nominalPrice = 10.0,
            localPrice = 12.0,
            operation = OPERATION.BUY
        )

        assertNull(operationIntent.id)
    }
}