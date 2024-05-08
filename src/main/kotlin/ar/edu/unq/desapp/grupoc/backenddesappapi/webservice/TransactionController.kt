package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.TransactionService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RequestVolumeDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseVolumeDTO
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
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints related with user transactions")
@Validated
class TransactionController {
    @Autowired private lateinit var transactionService: TransactionService

    // TODO: Procesar la transacción informada por un usuario (JUANMA)
    fun processTransaction() {

    }

    // TODO: Informar al usuario el volumen operado de cripto activos entre dos fechas (JUANMA)
    @PostMapping("/volume")
    fun informUserVolume(@Valid @RequestBody dates: RequestVolumeDTO): ResponseEntity<ResponseVolumeDTO> {
        return ResponseEntity.ok(transactionService.getVolumeBetweenDates(dates.from!!, dates.to!!))
    }
}