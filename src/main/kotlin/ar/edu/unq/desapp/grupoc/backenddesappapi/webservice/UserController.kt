package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException.BadRequest

@RestController
@RequestMapping("/users")
@Validated
@Tag(name = "Users", description = "Endpoints related with users data")
class UserController {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody  userInput: RegisterUserDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.status(200).body(userInput)
        } catch (error: BadRequest) {
            return ResponseEntity.status(400).body("No es por aca papi!")
        }
    }
}