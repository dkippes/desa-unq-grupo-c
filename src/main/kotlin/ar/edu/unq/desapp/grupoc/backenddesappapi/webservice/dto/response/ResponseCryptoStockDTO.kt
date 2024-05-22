package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response

import java.math.BigDecimal

data class ResponseCryptoStockDTO (
    val symbol: String,
    val price: BigDecimal,
    val quantity: Double,
    val localPrice: BigDecimal
)