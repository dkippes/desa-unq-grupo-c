package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterUserDTO(
    @field:Size(min = 3)
    var name: String,

    @field:Size(min = 3, max = 30)
    var lastName: String,

    @field:Email
    var email: String,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$")
    var password: String,

    @field:Size(min = 22, max = 22)
    var cvu: String,

    @field:Size(min = 3, max = 30)
    var address: String,

    @field:Size(min = 8, max = 8)
    var walletAddress: String
)