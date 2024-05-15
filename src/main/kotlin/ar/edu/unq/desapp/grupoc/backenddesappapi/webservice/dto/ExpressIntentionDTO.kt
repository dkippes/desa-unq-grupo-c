package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ExpressIntentionDTO(
    @field:NotNull(message = "Crypto is required")
    @JsonProperty("crypto")
    var cryptoAsset: SYMBOL?,

    @field:NotNull(message = "Nominal amount is required")
    @field:Positive(message = "Nominal amount must be positive")
    @JsonProperty("nominalAmount")
    var nominalAmount: BigDecimal?,

    @field:NotNull(message = "Operation type is required")
    @JsonProperty("operationType")
    var operationType: OPERATION?
) {
    fun isBuy(): Boolean {
        return operationType == OPERATION.BUY
    }
}