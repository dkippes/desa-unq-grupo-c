package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.utils.JwtUtil
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserAlreadyExistsException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestLoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestRegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseUserDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints related with users data")
@Validated
class UserController {
    @Autowired private lateinit var userService: UserService
    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var jwtUtil: JwtUtil

    @PostMapping("/auth/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided user data.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User registered successfully", content = [
                Content(schema = Schema(implementation = ResponseUserDTO::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad request: the provided user data is incorrect.", content = [
                Content(schema = Schema(implementation = MethodArgumentNotValidException::class))
            ]),
            ApiResponse(responseCode = "409", description = "User already exists", content = [
                Content(schema = Schema(implementation = UserAlreadyExistsException::class))
            ])
        ]
    )
    fun registerUser(@Valid @RequestBody userInput: RequestRegisterUserDTO): ResponseEntity<ResponseUserDTO> {
        val response = userService.registerUser(userInput)
        // TODO: Deberiamos mover esto a un controller en especifico?
        // TODO: Deberiamos incluir el token en el body de la respuesta?

        return authenticatedResponseEntity(userInput.email!!,userInput.password!!).body(response)
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Login a user", description = "Logs in a user with the provided email and password.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User logged in successfully", content = [
                Content(schema = Schema(implementation = ResponseUserDTO::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad request: the provided email or password are incorrect.", content = [
                Content(schema = Schema(implementation = MethodArgumentNotValidException::class))
            ]),
            ApiResponse(responseCode = "404", description = "User not found", content = [
                Content(schema = Schema(implementation = UserNotFoundException::class))
            ])
        ]
    )
    fun loginUser(@Valid @RequestBody userInput: RequestLoginUserDTO): ResponseEntity<ResponseUserDTO> {
        val response = userService.login(userInput)
        return authenticatedResponseEntity(userInput.email!!, userInput.password!!).body(response)
    }

    private fun authenticatedResponseEntity(email: String, password: String): ResponseEntity.BodyBuilder {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )
        val jwt = jwtUtil.generateToken(authentication.name)
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer $jwt")
        return ResponseEntity.ok().headers(headers)
    }
}