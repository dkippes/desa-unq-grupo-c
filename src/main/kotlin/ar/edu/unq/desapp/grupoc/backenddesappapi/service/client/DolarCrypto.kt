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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DolarCrypto

        if (sell != other.sell) return false
        if (buy != other.buy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sell.hashCode()
        result = 31 * result + buy.hashCode()
        return result
    }
}