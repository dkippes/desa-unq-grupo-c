package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/intent")
@Tag(name = "Intent", description = "Endpoints related with user intents")
class IntentController {

    // TODO: Permitir que un usuario exprese su intención de compra/venta (DIEGO)
    fun expressIntent() {

    }

    // TODO: Construir un listado donde se muestran las intenciones activas de compra/venta (DIEGO)
    fun listIntents() {

    }
}