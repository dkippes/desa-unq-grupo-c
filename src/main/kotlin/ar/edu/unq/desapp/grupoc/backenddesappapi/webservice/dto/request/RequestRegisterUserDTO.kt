package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RequestRegisterUserDTO(
    @field:Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters long.")
    @field:NotNull(message = "Name is required")
    @JsonProperty("name")
    var name: String?,

    @field:Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters long.")
    @field:NotNull(message = "Last name is required")
    @JsonProperty("lastName")
    var lastName: String?,

    @field:Email(message = "Email should be valid")
    @field:NotNull(message = "Email is required")
    @JsonProperty("email")
    var email: String?,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one special character and be at least 6 characters long.")
    @field:NotNull(message = "Password is required")
    @JsonProperty("password")
    var password: String?,

    @field:Size(min = 22, max = 22, message = "CVU must be 22 characters long.")
    @field:NotNull(message = "CVU is required")
    @JsonProperty("cvu")
    var cvu: String?,

    @field:Size(min = 3, max = 30, message = "Address must be between 3 and 30 characters long.")
    @field:NotNull(message = "Address is required")
    @JsonProperty("address")
    var address: String?,

    @field:Size(min = 8, max = 8, message = "Wallet address must be 8 characters long.")
    @field:NotNull(message = "Wallet address is required")
    @JsonProperty("walletAddress")
    var walletAddress: String?
) 