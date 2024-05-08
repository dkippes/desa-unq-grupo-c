package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ListCryotoActiveIntentionResponseDTO(
    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("reputation")
    val reputation: String,

    @JsonProperty("listActiveIntention")
    val listActiveIntention: List<ActiveIntentionResponseDTO>
)