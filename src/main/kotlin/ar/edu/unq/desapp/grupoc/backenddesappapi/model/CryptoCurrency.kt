package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "cryptocurrencies")
class CryptoCurrency(
    @Id
    @JsonProperty("symbol")
    var symbol: SYMBOL,
    @JsonProperty("price")
    var price: BigDecimal,
    var lastUpdateDateAndTime: LocalDateTime?
) : Serializable {
    override fun toString(): String {
        return "CryptoCurrency(symbol=$symbol, price=$price, lastUpdateDateAndTime=$lastUpdateDateAndTime)"
    }
}