package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class PersonaDTO( val nombre: String, val edad: Int)

@RestController
class PingController {
    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.status(200).body("Pong")
    }

    @Operation(summary = "Saluda a una persona", description = "Saluda a la persona cual nombre pases como parametro")
    @GetMapping("/saludar")
    fun saludar(@Parameter(description = "Nombre de la persona a saludar", required = true) @RequestParam nombre: String): ResponseEntity<String> {
        return ResponseEntity.status(200).body("Hola! Como estas ${nombre}")
    }

    @Tag(name = "Personas", description = "Endpoints relacionados con la gestión de personas")
    @PostMapping("/persona")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Usuario creado"),
        ApiResponse(responseCode = "404", description = "Usuario no creado")
    ])
    fun crearPersona(@RequestBody(description = "Datos de la persona a crear") datos: PersonaDTO): ResponseEntity<PersonaDTO> {
        return ResponseEntity.status(200).body(
            datos
        )
    }
}