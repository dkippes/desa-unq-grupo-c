package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty

data class ExpressIntentionResponseDTO(
    @JsonProperty("crypto")
    var cryptoAsset: SYMBOL?,

    @JsonProperty("nominal_amount")
    var nominalAmount: Double?,

    @JsonProperty("crypto_quote")
    var cryptoQuote: Double?,

    @JsonProperty("operation_amount_ars")
    var operationAmountARS: Double?,

    @JsonProperty("first_name")
    var firstName: String?,

    @JsonProperty("last_name")
    var lastName: String?,

    @JsonProperty("operation_type")
    var operationType: OPERATION?
)