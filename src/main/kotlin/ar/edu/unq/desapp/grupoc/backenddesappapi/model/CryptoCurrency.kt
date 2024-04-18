package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "cryptocurrencies")
class CryptoCurrency(
    @Id
    @JsonProperty("symbol")
    var symbol: SYMBOL,
    @JsonProperty("price")
    var price: Double,
    var lastUpdateDateAndTime: LocalDateTime = LocalDateTime.now()
)