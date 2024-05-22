package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class ResponseActiveIntentionDTO(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("created_date")
    val createdDate: LocalDateTime,

    @JsonProperty("symbol")
    val symbol: SYMBOL,

    @JsonProperty("nominalAmount")
    val nominalAmount: BigDecimal,

    @JsonProperty("cryptoQuote")
    val cryptoQuote: BigDecimal,

    @JsonProperty("localAmount")
    val localAmount: BigDecimal,
)