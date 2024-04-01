package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

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
    var intents: MutableList<CryptoOperationIntent> = mutableListOf<CryptoOperationIntent>()

    fun buy(crypto: SYMBOLS, quantity: Double, price: Double, localPrice: Double) {
        intents.add(
            CryptoOperationIntent(
                crypto,
                quantity,
                price,
                localPrice,
                OPERATION.BUY,
                this
            )
        )
    }

    fun sell(crypto: SYMBOLS, quantity: Double, price: Double, localPrice: Double) {
        intents.add(
            CryptoOperationIntent(
                crypto,
                quantity,
                price,
                localPrice,
                OPERATION.SELL,
                this
            )
        )
    }
}