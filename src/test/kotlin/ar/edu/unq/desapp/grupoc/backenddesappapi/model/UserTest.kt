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
            assertEquals(user.name, "Jose");
            assertEquals(user.cvu, "0123456789012345678912");
            assertEquals(user.email, "jose@gmail.com");
            assertEquals(user.lastName, "Del ñoca");
            assertEquals(user.password, "Se!23456");
            assertEquals(user.address, "Wilde 2034");
            assertEquals(user.walletAddress, "12345678")
        })
    }

    @Test
    fun assertEquality() {
        val userCopy = User(
            name = "Jose",
            cvu = "0123456789012345678912",
            email = "jose@gmail.com",
            lastName = "Del ñoca",
            password = "Se!23456",
            address = "Wilde 2034",
            walletAddress = "12345678"
        )
        val otherUser = User(
            name = "Marcos",
            cvu = "0123456763012345678912",
            email = "marcos@gmail.com",
            lastName = "Martinez",
            password = "Ro?64251",
            address = "Bernal 2034",
            walletAddress = "13246678"
        )

        assertAll({
            assertEquals(this.user,userCopy);
            assertEquals(this.user.hashCode(), userCopy.hashCode());
            assertNotEquals(this.user, otherUser);
            assertNotEquals(this.user.hashCode(), otherUser.hashCode())
        })

    }

    @Test
    fun `test publish buy`() {
        val symbol = SYMBOL.BTCUSDT
        val nominalQuantity = 0.5
        val nominalPrice = 50000.0
        val localPrice = 25000.0
        val operation = OPERATION.BUY

        val operationIntent = user.publish(symbol, nominalQuantity, nominalPrice, localPrice, operation)

        assertEquals(1, user.intents.size)
        assertEquals(operationIntent, user.intents[0])
    }

    @Test
    fun `test publish sell`() {
        val symbol = SYMBOL.BTCUSDT
        val nominalQuantity = 0.5
        val nominalPrice = 50000.0
        val localPrice = 25000.0
        val operation = OPERATION.SELL

        val operationIntent = user.publish(symbol, nominalQuantity, nominalPrice, localPrice, operation)

        assertEquals(1, user.intents.size)
        assertEquals(operationIntent, user.intents[0])
    }

    @Test
    fun `test confirmReception when hasCurrencyChanged is true`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_SENT

        assertThrows<PriceChangedOutOfLimitsException> {
            user.confirmReception(transaction, hasCurrencyChanged = true)
        }
    }

    @Test
    fun `test confirmReception when transaction status is cancelled`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.CANCELED

        assertThrows<OperationCancelledException> {
            user.confirmReception(transaction, hasCurrencyChanged = false)
        }
    }

    @Test
    fun `test confirmReception when transaction status is wating action`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.WAITING_ACTION

        assertThrows<TransferNotSentException> {
            user.confirmReception(transaction, hasCurrencyChanged = false)
        }
    }

    @Test
    fun `test confirmReception when transaction status is transfer sent`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_SENT

        user.confirmReception(transaction, hasCurrencyChanged = false)

        assertEquals(transaction.status, TransactionStatus.TRANSFER_RECEIVE)
    }

    @Test
    fun `test sendTransfer when hasCurrencyChanged is true`() {
        val transaction = Transaction()

        assertThrows<PriceChangedOutOfLimitsException> {
            user.sendTransfer(transaction, hasCurrencyChanged = true)
        }
        assertEquals(TransactionStatus.CANCELED, transaction.status)
    }

    @Test
    fun `test sendTransfer when transaction is already cancelled`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.CANCELED

        assertThrows<OperationCancelledException> {
            user.sendTransfer(transaction, hasCurrencyChanged = false)
        }
    }

    @Test
    fun `test sendTransfer when transaction is not waiting action`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_SENT

        assertThrows<TransferAlreadySentException> {
            user.sendTransfer(transaction, hasCurrencyChanged = false)
        }
    }

    @Test
    fun `test sendTransfer when transaction is waiting action`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.WAITING_ACTION

        user.sendTransfer(transaction, hasCurrencyChanged = false)

        assertEquals(TransactionStatus.TRANSFER_SENT, transaction.status)
    }

    @Test
    fun `test cancel when transaction is already cancelled`() {
        val transaction = Transaction(/* datos de la transacción */)
        transaction.status = TransactionStatus.CANCELED

        assertThrows<OperationCancelledException> {
            user.cancel(transaction)
        }
    }

    @Test
    fun `test cancel when transaction is transfer receive`() {
        val transaction = Transaction()
        transaction.status = TransactionStatus.TRANSFER_RECEIVE

        assertThrows<OperationFinishedException> {
            user.cancel(transaction)
        }
    }

    @Test
    fun `test cancel when transaction is not cancelled or transfer receive`() {
        val transaction = Transaction()

        val initialReputation = user.reputation
        val pointsPenalization = transaction.getPointsPenalizationForCancel()
        val expectedReputation = initialReputation - pointsPenalization

        user.cancel(transaction)

        assertEquals(TransactionStatus.CANCELED, transaction.status)
        assertEquals(expectedReputation, user.reputation)
    }
}