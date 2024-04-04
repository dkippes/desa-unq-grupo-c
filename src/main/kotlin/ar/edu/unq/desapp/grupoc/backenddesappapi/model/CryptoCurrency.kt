package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class CryptoCurrency {
    @Id
    var symbol: SYMBOL? = null
    var price: Float? = null
    var lastUpdateDateAndTime: String? = null

    fun setLastUpdateDateAndTime(lastUpdateDateAndTime: String?) {
        this.lastUpdateDateAndTime = lastUpdateDateAndTime
    }
}