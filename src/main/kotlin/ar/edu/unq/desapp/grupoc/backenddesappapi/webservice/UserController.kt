package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints related with users data")
class UserController {

    @Autowired
    private lateinit var userService : UserService

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userInput: RegisterUserDTO): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(userService.registerUser(userInput))
    }
}