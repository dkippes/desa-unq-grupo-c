package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ActiveIntentionResponseDTO(
    @JsonProperty("created_date")
    val createdDate: LocalDateTime,

    @JsonProperty("symbol")
    val symbol: SYMBOL,

    @JsonProperty("nominalAmount")
    val nominalAmount: Double,

    @JsonProperty("cryptoQuote")
    val cryptoQuote: Double,

    @JsonProperty("localAmount")
    val localAmount: Double,
)