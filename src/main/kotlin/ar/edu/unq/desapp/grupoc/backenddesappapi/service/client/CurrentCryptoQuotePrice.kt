package ar.edu.unq.desapp.grupoc.backenddesappapi.service.client

import java.io.Serializable
import java.time.LocalDateTime

class CurrentCryptoQuotePrice(var dateTime: LocalDateTime, var price: String) : Serializable