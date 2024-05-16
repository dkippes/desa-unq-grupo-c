package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
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

    fun generateNewTransaction(interestUser: Account): Transaction {
        if (operation == OPERATION.SELL) {
            this.transaction = Transaction(this, account, interestUser)
        } else {
            this.transaction = Transaction(this, interestUser, account)
        }
        return this.transaction!!
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OperationIntent

        if (symbol != other.symbol) return false
        if (nominalQuantity != other.nominalQuantity) return false
        if (nominalPrice != other.nominalPrice) return false
        if (localPrice != other.localPrice) return false
        if (operation != other.operation) return false
        if (account != other.account) return false
        if (status != other.status) return false
        if (transaction != other.transaction) return false
        if (createdDate != other.createdDate) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + nominalQuantity.hashCode()
        result = 31 * result + nominalPrice.hashCode()
        result = 31 * result + localPrice.hashCode()
        result = 31 * result + operation.hashCode()
        result = 31 * result + (account?.hashCode() ?: 0)
        result = 31 * result + status.hashCode()
        result = 31 * result + (transaction?.hashCode() ?: 0)
        result = 31 * result + createdDate.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}