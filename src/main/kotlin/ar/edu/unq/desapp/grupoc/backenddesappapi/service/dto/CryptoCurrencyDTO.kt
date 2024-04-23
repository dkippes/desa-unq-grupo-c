package ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty

class CryptoCurrencyDTO(
    @JsonProperty("symbol")
    var symbol: SYMBOL,
    @JsonProperty("price")
    var price: Double
)