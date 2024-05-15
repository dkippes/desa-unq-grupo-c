package ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class CryptoCurrencyDTO(
    @JsonProperty("symbol")
    var symbol: SYMBOL,
    @JsonProperty("price")
    var price: BigDecimal
)