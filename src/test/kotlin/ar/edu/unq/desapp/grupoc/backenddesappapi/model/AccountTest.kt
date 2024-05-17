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
import java.math.BigDecimal

class AccountTest {
    private lateinit var account : Account

    @BeforeEach
    fun setup() {
        account = Account(
            cvu = "0123456789012345678912",
            walletAddress = "12345678"
        )
    }

    @Test
    fun assertUserData() {
        assertAll({
            assertEquals(account.cvu, "0123456789012345678912");
            assertEquals(account.walletAddress, "12345678")
        })
    }

    @Test
    fun assertEquality() {
        val accountCopy = Account(
            cvu = "0123456789012345678912",
            walletAddress = "12345678"
        )
        val otherAccount = Account(
            cvu = "0123456763012345678912",
            walletAddress = "13246678"
        )

        assertAll({
            assertEquals(this.account,accountCopy);
            assertEquals(this.account.hashCode(), accountCopy.hashCode());
            assertNotEquals(this.account, otherAccount);
            assertNotEquals(this.account.hashCode(), otherAccount.hashCode())
        })

    }

    @Test
    fun `test publish buy`() {
        val symbol = SYMBOL.BTCUSDT
        val nominalQuantity = BigDecimal(0.5)
        val nominalPrice = BigDecimal(50000.0)
        val localPrice = BigDecimal(25000.0)
        val operation = OPERATION.BUY

        val operationIntent = account.publish(symbol, nominalQuantity, nominalPrice, localPrice, operation)

        assertEquals(1, account.intents.size)
        assertEquals(operationIntent, account.intents[0])
    }

    @Test
    fun `test publish sell`() {
        val symbol = SYMBOL.BTCUSDT
        val nominalQuantity = BigDecimal.valueOf(0.5)
        val nominalPrice = BigDecimal.valueOf(50000.0)
        val localPrice = BigDecimal.valueOf(25000.0)
        val operation = OPERATION.SELL

        val operationIntent = account.publish(symbol, nominalQuantity, nominalPrice, localPrice, operation)

        assertEquals(1, account.intents.size)
        assertEquals(operationIntent, account.intents[0])
    }


    @Test
    fun `test confirmReception when transaction status is cancelled`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.CANCELED

        assertThrows<OperationCancelledException> {
            account.confirmReception(transaction)
        }
    }

    @Test
    fun `test confirmReception when transaction status is wating action`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.WAITING_ACTION

        assertThrows<TransferNotSentException> {
            account.confirmReception(transaction)
        }
    }

    @Test
    fun `test confirmReception when transaction status is transfer sent`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_SENT

        account.confirmReception(transaction)

        assertEquals(transaction.status, TransactionStatus.TRANSFER_RECEIVE)
    }

    @Test
    fun `test sendTransfer when transaction is already cancelled`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.CANCELED

        assertThrows<OperationCancelledException> {
            account.sendTransfer(transaction)
        }
    }

    @Test
    fun `test sendTransfer when transaction is not waiting action`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_SENT

        assertThrows<TransferAlreadySentException> {
            account.sendTransfer(transaction)
        }
    }

    @Test
    fun `test sendTransfer when transaction is waiting action`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.WAITING_ACTION

        account.sendTransfer(transaction)

        assertEquals(TransactionStatus.TRANSFER_SENT, transaction.status)
    }

    @Test
    fun `test cancel when transaction is already cancelled`() {
        val transaction = Transaction(/* datos de la transacción */)
        transaction.status = TransactionStatus.CANCELED

        assertThrows<OperationCancelledException> {
            account.cancel(transaction)
        }
    }

    @Test
    fun `test cancel when transaction is transfer receive`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_RECEIVE

        assertThrows<OperationFinishedException> {
            account.cancel(transaction)
        }
    }

    @Test
    fun `test cancel when transaction is not cancelled or transfer receive`() {
        val transaction = Transaction()

        val initialReputation = 0
        val pointsPenalization = transaction.getPointsPenalizationForCancel()
        val expectedReputation = initialReputation.minus(pointsPenalization)

        account.cancel(transaction)

        assertEquals(TransactionStatus.CANCELED, transaction.status)
        assertEquals(expectedReputation, account.reputation)
    }

    @Test
    fun `hashCode should return same value for equal objects`() {
        val account1 = Account( "cvu123",  "Wallet Address 123")
        val account2 = Account( "cvu123",  "Wallet Address 123")

        assertEquals(account1.hashCode(), account2.hashCode())
    }
}