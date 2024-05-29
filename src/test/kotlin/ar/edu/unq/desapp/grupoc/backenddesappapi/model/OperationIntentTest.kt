package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.PriceChangedOutOfLimitsException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.math.BigDecimal

class OperationIntentTest() {

    private var operationIntent1: OperationIntent = OperationIntent(
        symbol = SYMBOL.ADAUSDT,
        nominalQuantity = BigDecimal.valueOf(100.0),
        nominalPrice = BigDecimal.valueOf(10.0),
        localPrice = BigDecimal.valueOf(12.0),
        operation = OPERATION.BUY,
        account = Account(walletAddress = "as", cvu="ed"),
        status = OperationStatus.OPEN,
        transaction = Transaction(),

    )

    @Test
    fun shouldBeActiveWhenStatusIsOpen() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY
        )
        Assertions.assertTrue(operationIntent.isActive())
    }

    @Test
    fun shouldNotBeActiveWhenStatusIsClosed() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY,
            status = OperationStatus.CLOSED
        )
        Assertions.assertFalse(operationIntent.isActive())
    }

    @Test
    fun shouldGenerateASellTransactionWhenUsersAreValid() {
        val seller = mock(Account::class.java)
        val buyer = mock(Account::class.java)
        Mockito.`when`(seller.id).thenReturn(1)
        Mockito.`when`(buyer.id).thenReturn(2)
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.SELL,
            account = seller
        )

        operationIntent.generateNewTransaction(buyer, 10.0)

        Assertions.assertNotNull(operationIntent.transaction)
        Assertions.assertEquals(operationIntent.transaction!!.status, TransactionStatus.WAITING_ACTION)
        Assertions.assertEquals(seller, operationIntent.transaction?.seller)
        Assertions.assertEquals(buyer, operationIntent.transaction?.buyer)
    }

    @Test
    fun shouldGenerateABuyTransactionWhenUsersAreValid() {
        val user1 = mock(Account::class.java)
        val user2 = mock(Account::class.java)
        Mockito.`when`(user1.id).thenReturn(1)
        Mockito.`when`(user2.id).thenReturn(2)
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY,
            account = user1
        )

        operationIntent.generateNewTransaction(user2, 10.0)

        Assertions.assertNotNull(operationIntent.transaction)
        Assertions.assertEquals(operationIntent.transaction!!.status, TransactionStatus.WAITING_ACTION)
        Assertions.assertEquals(user2, operationIntent.transaction?.seller)
        Assertions.assertEquals(user1, operationIntent.transaction?.buyer)
    }

    @Test
    fun `test operation intent id should be null after instantiation`() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY
        )

        assertNull(operationIntent.id)
    }

    @Test
    fun `test operation intent equals should return true when comparing with itself`() {
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY
        )

        Assertions.assertTrue(operationIntent.equals(operationIntent))
    }


    @Test
    fun `test operation intent should cancel a transaction when the price exceeds limits`() {
        val user1 = mock(Account::class.java)
        val user2 = mock(Account::class.java)
        Mockito.`when`(user1.id).thenReturn(1)
        Mockito.`when`(user2.id).thenReturn(2)
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY,
            account = user1
        )



        Assertions.assertThrows(PriceChangedOutOfLimitsException::class.java){
            operationIntent.generateNewTransaction(user2, 1000.0)
        }
        Assertions.assertNotNull(operationIntent.transaction)
        Assertions.assertEquals(operationIntent.transaction!!.status, TransactionStatus.CANCELED)
        Assertions.assertEquals(OperationStatus.CLOSED, operationIntent.transaction?.intention?.status)
    }

    @Test
    fun `test operation intent should not be able to create a transaction with the same user`() {
        val user1 = mock(Account::class.java)
        Mockito.`when`(user1.id).thenReturn(1)
        val operationIntent = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY,
            account = user1
        )

        Assertions.assertThrows(IllegalArgumentException::class.java){
            operationIntent.generateNewTransaction(user1, 10.0)
        }
    }

    @Test
    fun assertEquality() {
        val operationIntent2 = OperationIntent(
            symbol = SYMBOL.ADAUSDT,
            nominalQuantity = BigDecimal.valueOf(100.0),
            nominalPrice = BigDecimal.valueOf(10.0),
            localPrice = BigDecimal.valueOf(12.0),
            operation = OPERATION.BUY,
            account = Account(walletAddress = "as", cvu="ed"),
            status = OperationStatus.OPEN,
            transaction = Transaction(),
        )

        Assertions.assertAll({
            assertEquals(operationIntent1, operationIntent1);
            assertEquals(operationIntent1.hashCode(), operationIntent1.hashCode());
            assertNotEquals(operationIntent1, operationIntent2);
            assertNotEquals(operationIntent1.hashCode(), operationIntent2.hashCode())
        })
    }

}