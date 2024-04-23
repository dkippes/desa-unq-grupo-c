package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterUserDTO(
    @field:Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    @JsonProperty("name")
    var name: String,

    @field:Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters long.")
    @JsonProperty("lastName")
    var lastName: String,

    @field:Email(message = "Email should be valid")
    @JsonProperty("email")
    var email: String,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one special character and be at least 6 characters long.")
    @JsonProperty("password")
    var password: String,

    @field:Size(min = 22, max = 22, message = "CVU must be 22 characters long.")
    @JsonProperty("cvu")
    var cvu: String,

    @field:Size(min = 3, max = 30, message = "Address must be between 3 and 30 characters long.")
    @JsonProperty("address")
    var address: String,

    @field:Size(min = 8, max = 8, message = "Wallet address must be 8 characters long.")
    @JsonProperty("walletAddress")
    var walletAddress: String
) 