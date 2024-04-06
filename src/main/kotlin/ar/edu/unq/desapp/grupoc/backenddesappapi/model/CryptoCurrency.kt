package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class CryptoCurrency(
    @Id
    var symbol: SYMBOL,
    var price: Double,
    var lastUpdateDateAndTime: LocalDateTime = LocalDateTime.now()
)