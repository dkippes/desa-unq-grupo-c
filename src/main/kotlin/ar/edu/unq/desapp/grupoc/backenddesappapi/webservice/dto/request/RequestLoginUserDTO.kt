package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class RequestLoginUserDTO(
    @field:Email(message = "Email should be valid")
    @field:NotNull(message = "Email is required")
    @JsonProperty("email")
    var email: String?,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one special character and be at least 6 characters long.")
    @JsonProperty("password")
    @field:NotNull(message = "Password is required")
    var password: String?
)