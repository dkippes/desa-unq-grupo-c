package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class CryptoOperationIntent (
    var symbol: SYMBOLS,
    var nominalQuantity: Double,
    var nominalPrice: Double,
    var localPrice: Double,
    var operation: OPERATION,
    @ManyToOne var user: User? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    var id: Long? = null
    var isActive: Boolean = true


    fun closeOperation() {
        this.isActive = false
    }
}