package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class PersonaDTO(@NotNull val nombre: String, @NotBlank val edad: Int)

@RestController
class PingController {
    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> {
        return ResponseEntity.status(200).body("Pong")
    }

    @GetMapping("/saludar")
    fun saludar(@RequestParam nombre: String): ResponseEntity<String> {
        return ResponseEntity.status(200).body("Hola! Como estas ${nombre}")
    }

    @PostMapping("/persona")
    fun crearPersona(@RequestBody datos: PersonaDTO): ResponseEntity<PersonaDTO> {
        return ResponseEntity.status(200).body(
            datos
        )
    }
}