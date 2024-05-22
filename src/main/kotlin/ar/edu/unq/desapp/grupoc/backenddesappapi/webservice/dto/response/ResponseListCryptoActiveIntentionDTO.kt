package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseListCryptoActiveIntentionDTO(
    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("reputation")
    val reputation: String,

    @JsonProperty("activeIntentions")
    val listActiveIntention: List<ResponseActiveIntentionDTO>
)