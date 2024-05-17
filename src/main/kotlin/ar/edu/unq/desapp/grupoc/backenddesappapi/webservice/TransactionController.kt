package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.*
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints related with user transactions")
@Validated
class TransactionController {
    @Autowired private lateinit var transactionService: TransactionService

    @PutMapping("/{transactionId}/process")
    fun processTransaction(@PathVariable transactionId: Long, @Valid @RequestBody data: RequestProcessDTO): ResponseEntity<ResponseTransactionDTO> {
        return ResponseEntity.ok(transactionService.processTransaction(data.userId!!, transactionId, data.action!!))
    }

    @PostMapping("/generate")
    fun generateTransaction(@Valid @RequestBody data: RequestCreateTransactionDTO): ResponseEntity<ResponseTransactionDTO> {
        // TODO: Ver que id usar con diego si el de la cuenta o el del usuario.
        return ResponseEntity.ok(transactionService.generateTransaction(data.userId!!, data.operationId!!))
    }

    @PostMapping("/volume/{userId}")
    fun informUserVolume(@PathVariable("userId") userId: Long, @Valid @RequestBody dates: RequestVolumeDTO): ResponseEntity<ResponseVolumeDTO> {
        return ResponseEntity.ok(transactionService.getVolumeBetweenDates(userId, dates.from!!, dates.to!!))
    }
}