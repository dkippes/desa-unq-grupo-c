package ar.edu.unq.desapp.grupoc.backenddesappapi.model

class User() {
    var id: Long? = null
    var name: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null
    var cvu: String? = null
    var address: String? = null
    var walletAddress: String? = null
    var reputation: Int = 0

    constructor(
        name: String?,
        lastName: String?,
        email: String?,
        password: String?,
        cvu: String?,
        address: String?,
        walletAddress: String?
    ) : this() {
        this.name = name
        this.lastName = lastName
        this.email = email
        this.password = password
        this.cvu = cvu
        this.address = address
        this.walletAddress = walletAddress
    }
}