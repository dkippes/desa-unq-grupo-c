package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.log.LogExecutionTime
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.IntentService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseListCryptoActiveIntentionDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/intent")
@Tag(name = "Intent", description = "Endpoints related with user intents")
class IntentController {

    @Autowired
    private lateinit var intentService: IntentService

    @PostMapping("/express/{userId}")
    @Operation(summary = "Express intent", description = "Expresses the intent of a user to buy or sell a cryptocurrency.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Intent expressed successfully", content = [
                Content(schema = Schema(implementation = ResponseExpressIntentionDTO::class))
            ]),
            ApiResponse(responseCode = "404", description = "User not found", content = [
                Content(schema = Schema(implementation = UserNotFoundException::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad request: the provided intent data is incorrect.", content = [
                Content(schema = Schema(implementation = MethodArgumentNotValidException::class))
            ])
        ]
    )
    @LogExecutionTime
    fun expressIntent(
        @PathVariable("userId") userId: Long,
        @Valid @RequestBody userInput: RequestExpressIntentionDTO
    ): ResponseEntity<ResponseExpressIntentionDTO> {
        return ResponseEntity.ok(intentService.expressIntention(userInput, userId))
    }

    @GetMapping("/list-active/{userId}")
    @Operation(summary = "List active intents", description = "Lists the active intents of a user.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Intents listed successfully", content = [
                Content(schema = Schema(implementation = ResponseExpressIntentionDTO::class))
            ]),
            ApiResponse(responseCode = "404", description = "User not found", content = [
                Content(schema = Schema(implementation = UserNotFoundException::class))
            ])
        ]
    )
    @LogExecutionTime
    fun listIntents(@PathVariable("userId") userId: Long): ResponseEntity<ResponseListCryptoActiveIntentionDTO> {
        return ResponseEntity.ok(intentService.listActiveIntentionResponseDTO(userId))
    }
}