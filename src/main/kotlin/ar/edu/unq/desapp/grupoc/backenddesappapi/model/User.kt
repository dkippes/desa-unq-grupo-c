package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    var name: String,
    var lastName: String,
    @Column(unique = true)
    var email: String,
    var password: String,
    var address: String,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var account: Account? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (address != other.address) return false
        if (password != other.password) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    fun getOperationsReputations(): String {
        if (account?.intents!!.isNotEmpty()) {
            val reputation = account?.reputation!!
            val totalOperations = account?.intents!!.size
            return (reputation / totalOperations).toString()
        }
        return "Sin operaciones"
    }
}