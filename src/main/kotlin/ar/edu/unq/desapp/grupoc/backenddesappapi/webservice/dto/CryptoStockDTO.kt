package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import java.math.BigDecimal

data class CryptoStockDTO (
    val symbol: String,
    val price: BigDecimal,
    val quantity: Double,
    val localPrice: BigDecimal
)