package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.CryptoCurrencyNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crypto")
@Tag(name = "Crypto", description = "Endpoints related with cryptocurrencies data")
class CryptoController {
    @Autowired lateinit var cryptoService: CryptoService

    @GetMapping("/currencies")
    @Operation(summary = "Get all cryptocurrencies", description = "Returns a list of all cryptocurrencies with their current prices.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Cryptocurrencies retrieved successfully", content = [
            Content(schema = Schema(implementation = CryptoCurrencyList::class))
        ])
    ])
    fun currencies(): ResponseEntity<CryptoCurrencyList> {
      return  ResponseEntity.ok(cryptoService.getAllCryptoCurrencyPrices())
    }

    @GetMapping("/currency/{symbol}")
    @Operation(summary = "Get a cryptocurrency", description = "Returns a cryptocurrency with its current price.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Cryptocurrency retrieved successfully", content = [
            Content(schema = Schema(implementation = CryptoCurrency::class))
        ]),
        ApiResponse(responseCode = "404", description = "Cryptocurrency not found", content = [
            Content(schema = Schema(implementation = CryptoCurrencyNotFoundException::class))
        ])
    ])
    fun currency(@PathVariable symbol: SYMBOL): ResponseEntity<CryptoCurrency> {
        return ResponseEntity.ok(cryptoService.getCryptoCurrencyPrice(symbol))
    }

    // TODO: Informar al usuario el volumen operado de cripto activos entre dos fechas (JUANMA)
    fun informUserVolume() {

    }
}