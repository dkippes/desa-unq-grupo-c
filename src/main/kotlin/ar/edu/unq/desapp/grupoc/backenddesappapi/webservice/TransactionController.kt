package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.configuration.log.LogExecutionTime
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions.PriceChangedOutOfLimitsException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.CryptoCurrencyNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestCreateTransactionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestProcessDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestVolumeDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseTransactionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseVolumeDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints related with user transactions")
@Validated
class TransactionController {
    @Autowired
    private lateinit var transactionService: TransactionService

    @PutMapping("/{transactionId}/process")
    @Operation(summary = "Process transaction", description = "Processes a transaction with the provided data.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Transaction processed successfully", content = [
                    Content(schema = Schema(implementation = ResponseTransactionDTO::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "User not found", content = [
                    Content(schema = Schema(implementation = UserNotFoundException::class))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request: the provided transaction data is incorrect.",
                content = [
                    Content(schema = Schema(implementation = MethodArgumentNotValidException::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "Transaction not found", content = [
                    Content(schema = Schema(implementation = EntityNotFoundException::class))
                ]
            ),
            ApiResponse(
                responseCode = "405", description = "Invalid action", content = [
                    Content(schema = Schema(implementation = IllegalArgumentException::class))
                ]
            )
        ]
    )
    @LogExecutionTime
    fun processTransaction(
        @PathVariable transactionId: Long,
        @Valid @RequestBody data: RequestProcessDTO
    ): ResponseEntity<ResponseTransactionDTO> {
        return ResponseEntity.ok(transactionService.processTransaction(data.userId!!, transactionId, data.action!!))
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate transaction", description = "Generates a transaction with the provided data.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Transaction generated successfully", content = [
                    Content(schema = Schema(implementation = ResponseTransactionDTO::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "User not found", content = [
                    Content(schema = Schema(implementation = UserNotFoundException::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "Operation not found", content = [
                    Content(schema = Schema(implementation = EntityNotFoundException::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "Cryptocurrency not found", content = [
                    Content(schema = Schema(implementation = CryptoCurrencyNotFoundException::class))
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "Transaction already generated.", content = [
                    Content(schema = Schema(implementation = BadRequestException::class))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Price changed out of limits.",
                content = [
                    Content(schema = Schema(implementation = PriceChangedOutOfLimitsException::class))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "User is the same as the operation user.",
                content = [
                    Content(schema = Schema(implementation = IllegalArgumentException::class))
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request: the provided transaction data is incorrect.",
                content = [
                    Content(schema = Schema(implementation = MethodArgumentNotValidException::class))
                ]
            )
        ]
    )
    @LogExecutionTime
    fun generateTransaction(@Valid @RequestBody data: RequestCreateTransactionDTO): ResponseEntity<ResponseTransactionDTO> {
        return ResponseEntity.ok(transactionService.generateTransaction(data.userId!!, data.operationId!!))
    }

    @PostMapping("/volume/{userId}")
    @Operation(summary = "Inform user volume", description = "Informs the user volume between two dates.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "User volume informed successfully", content = [
                    Content(schema = Schema(implementation = ResponseVolumeDTO::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "User not found", content = [
                    Content(schema = Schema(implementation = UserNotFoundException::class))
                ]
            ),
            ApiResponse(
                responseCode = "400", description = "Bad request: the provided dates are incorrect.", content = [
                    Content(schema = Schema(implementation = MethodArgumentNotValidException::class))
                ]
            )
        ]
    )
    @LogExecutionTime
    fun informUserVolume(
        @PathVariable("userId") userId: Long,
        @Valid @RequestBody dates: RequestVolumeDTO
    ): ResponseEntity<ResponseVolumeDTO> {
        return ResponseEntity.ok(transactionService.getVolumeBetweenDates(userId, dates.from!!, dates.to!!))
    }
}