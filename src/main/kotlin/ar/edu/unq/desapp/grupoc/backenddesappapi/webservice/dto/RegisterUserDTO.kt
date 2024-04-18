package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterUserDTO(
    @field:Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    var name: String,

    @field:Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters long.")
    var lastName: String,

    @field:Email(message = "Email should be valid")
    var email: String,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one special character and be at least 6 characters long.")
    var password: String,

    @field:Size(min = 22, max = 22, message = "CVU must be 22 characters long.")
    var cvu: String,

    @field:Size(min = 3, max = 30, message = "Address must be between 3 and 30 characters long.")
    var address: String,

    @field:Size(min = 8, max = 8, message = "Wallet address must be 8 characters long.")
    var walletAddress: String
)