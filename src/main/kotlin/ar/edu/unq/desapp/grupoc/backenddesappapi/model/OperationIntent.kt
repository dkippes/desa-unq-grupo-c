package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class OperationIntent (
    var symbol: SYMBOL,
    var nominalQuantity: Double,
    var nominalPrice: Double,
    var localPrice: Double,
    var operation: OPERATION,
    var status: OperationStatus = OperationStatus.OPEN,
    @OneToOne var seller: User? = null,
    @OneToOne var buyer: User? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    var id: Long? = null

    fun isActive(): Boolean = this.status === OperationStatus.OPEN

}