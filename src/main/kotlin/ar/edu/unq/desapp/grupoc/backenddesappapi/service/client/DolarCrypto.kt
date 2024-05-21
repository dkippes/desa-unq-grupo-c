package ar.edu.unq.desapp.grupoc.backenddesappapi.service.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
class DolarCrypto(
    @JsonProperty("compra")
    val sell: BigDecimal,
    @JsonProperty("venta")
    val buy: BigDecimal,
) {
}