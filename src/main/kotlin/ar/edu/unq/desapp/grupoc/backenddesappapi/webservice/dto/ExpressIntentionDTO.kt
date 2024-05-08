package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class ExpressIntentionDTO(
    @field:NotNull(message = "Crypto is required")
    @JsonProperty("crypto")
    var cryptoAsset: SYMBOL?,

    @field:NotNull(message = "Nominal amount is required")
    @JsonProperty("nominalAmount")
    var nominalAmount: Double?,

    @field:NotNull(message = "Operation type is required")
    @JsonProperty("operationType")
    var operationType: OPERATION?
)