package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.helpers.Factory
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseUserDTO
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints related with users data")
@Validated
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userInput: RegisterUserDTO): ResponseEntity<ResponseUserDTO> {
        // Validar con try y catch - Ver middleware como solucion
        try {
            return ResponseEntity.ok(
                Factory.createDTOFromUser(
                    userService.registerUser(userInput)
                )
            )
        } catch(err: Throwable) {
            print("Error")
            // TODO( catch various errors )
            throw err
        }
    }
}