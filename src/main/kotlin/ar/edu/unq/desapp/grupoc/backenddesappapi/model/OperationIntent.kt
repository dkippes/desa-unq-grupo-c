package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import jakarta.persistence.*

@Entity
class OperationIntent (
    var symbol: SYMBOL,
    var nominalQuantity: Double,
    var nominalPrice: Double,
    var localPrice: Double,
    var operation: OPERATION,
    @OneToOne var user: User? = null,
    var status: OperationStatus = OperationStatus.OPEN,
    @OneToOne var transaction: Transaction? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    var id: Long? = null

    fun isActive(): Boolean = this.status === OperationStatus.OPEN

    fun generateNewTransaction(interestUser: User) {
        if (operation == OPERATION.SELL) {
            this.transaction = Transaction(this, user, interestUser)
        } else {
            this.transaction = Transaction(this, interestUser, user)
        }
    }
}