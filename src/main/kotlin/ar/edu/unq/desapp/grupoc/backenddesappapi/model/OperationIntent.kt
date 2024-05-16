package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.PriceChangedOutOfLimitsException
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "operation_intents")
class OperationIntent (
    var symbol: SYMBOL,
    @Column(precision=100, scale=10)
    var nominalQuantity: BigDecimal,
    var nominalPrice: BigDecimal,
    var localPrice: BigDecimal,
    var operation: OPERATION,
    @ManyToOne(fetch = FetchType.LAZY)
    var account: Account? = null,
    var status: OperationStatus = OperationStatus.OPEN,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var transaction: Transaction? = null,
    var createdDate: LocalDateTime = LocalDateTime.now(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun isActive(): Boolean = this.status === OperationStatus.OPEN

    fun generateNewTransaction(interestUser: Account, currentPrice: Double): Transaction {
        if (operation == OPERATION.SELL) {
            this.transaction = Transaction(this, account, interestUser)
        } else {
            this.transaction = Transaction(this, interestUser, account)
        }
        validateTransaction(currentPrice)
        return this.transaction!!
    }

    private fun validateTransaction(currentPrice: Double) {
        val margin = currentPrice.times(0.05)
        val mustBeCancelled = nominalPrice.toDouble() <= (currentPrice.minus(margin)) || nominalPrice.toDouble() >= (currentPrice.plus(margin))
        if (mustBeCancelled) {
            println(listOf(nominalPrice, currentPrice, margin))
            this.transaction!!.cancelBySystem()
            throw PriceChangedOutOfLimitsException()
        }
    }
}