package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.CryptoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/crypto")
class CryptoController {
    @Autowired lateinit var cryptoService: CryptoService

    @GetMapping("/currencies")
    fun currencies(): ResponseEntity<CryptoCurrencyList> {
      return  ResponseEntity.ok(cryptoService.getAllCryptoCurrencyPrices())
    }

    @GetMapping("/currency/{symbol}")
    fun currency(@PathVariable symbol: SYMBOL): ResponseEntity<CryptoCurrency> {
        // TODO: refactorear toda la logica...
        return ResponseEntity.ok(cryptoService.getCryptoCurrencyPrice(symbol))
    }
}