package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

data class LoginUserDTO(
    @field:Email(message = "Email should be valid")
    var email: String,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one special character and be at least 6 characters long.")
    var password: String
)