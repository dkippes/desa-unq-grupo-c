package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import jakarta.validation.constraints.*

data class RegisterUserDTO(@NotNull @Size(min = 3, max = 30)
                           var name: String,
                           @NotNull @Size(min = 3, max = 30)
                           var lastName: String,
                           @NotNull @Email
                           var email: String,
                           @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{6,}$")
                           var password: String,
                           @NotNull @Size(min = 22, max = 22)
                           var cvu: String,
                           @NotNull @Size(min = 3, max = 30)
                           var address: String,
                           @NotNull @Size(min = 8, max = 8)
                           var walletAddress: String)