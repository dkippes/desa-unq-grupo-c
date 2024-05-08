package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.*
import jakarta.persistence.*

@Entity
@Table(name = "accounts")
class Account (
    var cvu: String,
    var walletAddress: String,
    var reputation: Int? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    var intents: MutableList<OperationIntent> = mutableListOf()


    fun publish(symbol: SYMBOL, nominalQuantity: Double, nominalPrice: Double, localPrice: Double, operation: OPERATION): OperationIntent {
        val operationIntent = OperationIntent(
            symbol = symbol,
            nominalQuantity = nominalQuantity,
            nominalPrice = nominalPrice,
            localPrice = localPrice,
            operation,
            this
        )
        this.intents.add(operationIntent)
        return operationIntent
    }

    fun confirmReception(transaction: Transaction, hasCurrencyChanged: Boolean) {
        if (hasCurrencyChanged) {
            transaction.status = TransactionStatus.CANCELED
            throw PriceChangedOutOfLimitsException()
        }

        validateIfOperationWasCancelled(transaction)

        if (transaction.status != TransactionStatus.TRANSFER_SENT) {
            throw TransferNotSentException()
        }
        transaction.status = TransactionStatus.TRANSFER_RECEIVE
        this.increasePoints(transaction.getPointsForFinish())
    }

    fun sendTransfer(transaction: Transaction, hasCurrencyChanged: Boolean) {
        if (hasCurrencyChanged) {
            transaction.status = TransactionStatus.CANCELED
            throw PriceChangedOutOfLimitsException()
        }

        validateIfOperationWasCancelled(transaction)

        if (transaction.status != TransactionStatus.WAITING_ACTION) {
            throw TransferAlreadySentException()
        }
        transaction.status = TransactionStatus.TRANSFER_SENT
    }
    fun cancel(transaction: Transaction) {
        validateIfOperationWasCancelled(transaction)

        if (transaction.status == TransactionStatus.TRANSFER_RECEIVE) {
            throw OperationFinishedException()
        }

        this.decreaseReputationPoints(transaction.getPointsPenalizationForCancel())
        transaction.status = TransactionStatus.CANCELED
    }

    private fun validateIfOperationWasCancelled(transaction: Transaction) {
        if(transaction.status === TransactionStatus.CANCELED) {
            throw OperationCancelledException()
        }
    }

    private fun increasePoints(points: Int) {
        if (this.reputation == null) {
            this.reputation = 0
        }
        this.reputation = this.reputation?.plus(points)
    }
    private fun decreaseReputationPoints(points: Int) {
        if (this.reputation == null) {
            this.reputation = 0
        }
        this.reputation = this.reputation!! - points
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (cvu != other.cvu) return false
        if (walletAddress != other.walletAddress) return false
        if (reputation != other.reputation) return false
        if (id != other.id) return false
        if (intents != other.intents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cvu.hashCode()
        result = 31 * result + walletAddress.hashCode()
        result = 31 * result + reputation!!
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + intents.hashCode()
        return result
    }


}