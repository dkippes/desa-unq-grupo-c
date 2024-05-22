package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class RequestVolumeDTO(
    @field:NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("from")
    val from: LocalDate?,
    @field:NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("to") val to: LocalDate?,
) {
    fun toJson(): String {
        return "{\"from\":\"$from\", \"to\":\"$to\"}"
    }
}