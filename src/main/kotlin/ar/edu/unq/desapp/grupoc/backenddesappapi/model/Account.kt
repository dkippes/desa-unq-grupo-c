package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.OperationCancelledException
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.OperationFinishedException
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.TransferAlreadySentException
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.TransferNotSentException
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "accounts")
class Account (
    var cvu: String,
    var walletAddress: String,
    var reputation: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    var intents: MutableList<OperationIntent> = mutableListOf()
    @OneToMany(mappedBy = "seller", cascade = [CascadeType.ALL])
    var sellerTransactions: MutableList<Transaction> = mutableListOf()
    @OneToMany(mappedBy = "buyer", cascade = [CascadeType.ALL])
    var buyerTransactions: MutableList<Transaction> = mutableListOf()

    @OneToOne(cascade = [CascadeType.ALL])
    var user: User? = null


    fun publish(symbol: SYMBOL, nominalQuantity: BigDecimal, nominalPrice: BigDecimal, localPrice: BigDecimal, operation: OPERATION): OperationIntent {
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

    fun confirmReception(transaction: Transaction) {
        validateIfOperationWasCancelled(transaction)

        if (transaction.status != TransactionStatus.TRANSFER_SENT) {
            throw TransferNotSentException()
        }
        transaction.status = TransactionStatus.TRANSFER_RECEIVE
        transaction.buyer?.increasePoints(transaction.getPointsForFinish())
        transaction.seller?.increasePoints(transaction.getPointsForFinish())
        transaction.intention?.status = OperationStatus.CLOSED
    }

    fun sendTransfer(transaction: Transaction) {
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
        transaction.intention?.status = OperationStatus.CLOSED
    }

    private fun validateIfOperationWasCancelled(transaction: Transaction) {
        if(transaction.status === TransactionStatus.CANCELED) {
            throw OperationCancelledException()
        }
    }

    private fun increasePoints(points: Int) {
        this.reputation += points
    }
    private fun decreaseReputationPoints(points: Int) {
        this.reputation -= points
    }

    fun getOperationsReputations(): String {
        if (intents.isNotEmpty()) {
            val reputation = reputation
            val totalOperations = intents.size
            return (reputation / totalOperations).toString()
        }
        return "Sin operaciones"
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
        result = 31 * result + reputation.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + intents.hashCode()
        return result
    }

    fun getTimesOperated(): Int {
        return sellerTransactions.size + buyerTransactions.size

    }


}