package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class RequestExpressIntentionDTO(
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequestExpressIntentionDTO

        if (cryptoAsset != other.cryptoAsset) return false
        if (nominalAmount != other.nominalAmount) return false
        if (operationType != other.operationType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cryptoAsset?.hashCode() ?: 0
        result = 31 * result + (nominalAmount?.hashCode() ?: 0)
        result = 31 * result + (operationType?.hashCode() ?: 0)
        return result
    }
}