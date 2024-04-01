package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

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
}