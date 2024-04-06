package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.*
import jakarta.persistence.*

@Entity
class User(
    var name: String,
    var lastName: String,
    var email: String,
    var password: String,
    var cvu: String,
    var address: String,
    var walletAddress: String,
    var reputation: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @OneToMany
    var intents: MutableList<OperationIntent> = mutableListOf<OperationIntent>()

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
        if(transaction.status === TransactionStatus.CANCELED) {
            throw OperationCancelledException()
        }

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

        if(transaction.status === TransactionStatus.CANCELED) {
            throw OperationCancelledException()
        }

        if (transaction.status != TransactionStatus.WAITING_ACTION) {
            throw TransferAlreadySentException()
        }
        transaction.status = TransactionStatus.TRANSFER_SENT
    }
    fun cancel(transaction: Transaction) {
        if(transaction.status === TransactionStatus.CANCELED) {
            throw OperationCancelledException()
        }

        if (transaction.status == TransactionStatus.TRANSFER_RECEIVE) {
            throw OperationFinishedException()
        }

        this.decreaseReputationPoints(transaction.getPointsPenalizationForCancel())
        transaction.status = TransactionStatus.CANCELED
    }

    private fun increasePoints(points: Int) {
        this.reputation += points
    }
    private fun decreaseReputationPoints(points: Int) {
        this.reputation -= points
    }
}