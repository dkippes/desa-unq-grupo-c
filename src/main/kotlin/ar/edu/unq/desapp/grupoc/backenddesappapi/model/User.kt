package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
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
    var intents: MutableList<OperationIntent> = mutableListOf<OperationIntent>()

    fun buy() {}
    fun sell() {}
    fun confirmReception() {}
    fun sendTransfer() {}
    fun cancel() {}
}