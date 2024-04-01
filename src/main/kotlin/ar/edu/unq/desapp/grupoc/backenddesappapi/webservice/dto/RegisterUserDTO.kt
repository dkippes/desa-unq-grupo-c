package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated

@Validated
class RegisterUserDTO {
    @Size(min = 3, max = 30)
    @NotNull
    var name: String? = null

    @field:NotNull
    @field:Size(min = 3, max = 30)
    lateinit var lastName: String
    @field:NotNull
    @field:Email
    lateinit var email: String
    @field:NotNull
    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$")
    lateinit var password: String
    @field:NotNull
    @field:Size(min = 22, max = 22)
    lateinit var cvu: String
    @field:NotNull
    @field:Size(min = 3, max = 30)
    lateinit var address: String
    @field:NotNull
    @field:Size(min = 8, max = 8)
    lateinit var walletAddress: String

    constructor(
        name: String?,
        lastName: String,
        email: String,
        password: String,
        cvu: String,
        address: String,
        walletAddress: String
    )
}