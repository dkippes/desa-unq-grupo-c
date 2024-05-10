package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

data class CryptoStockDTO (
    val symbol: String,
    val price: Double,
    val quantity: Double,
    val localPrice: Double
)