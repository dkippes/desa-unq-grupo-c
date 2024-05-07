package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Endpoints related with user transactions")
class TransactionController {
    // TODO: Procesar la transacción informada por un usuario (JUANMA)
    fun processTransaction() {

    }
}