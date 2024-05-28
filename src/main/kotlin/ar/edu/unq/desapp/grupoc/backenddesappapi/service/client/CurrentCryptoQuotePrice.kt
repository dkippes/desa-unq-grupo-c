package ar.edu.unq.desapp.grupoc.backenddesappapi.service.client

import java.io.Serializable
import java.time.LocalDateTime

class CurrentCryptoQuotePrice(var dateTime: LocalDateTime, var price: String) : Serializable {
    override fun toString(): String {
        return "CurrentCryptoQuotePrice(dateTime=$dateTime, price='$price')"
    }
}