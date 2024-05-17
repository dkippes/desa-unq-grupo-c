package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import java.math.BigDecimal

class ResponseTransactionDTO(
    val id : Long,
    val price: BigDecimal,
    val amount: BigDecimal,
    val fullName: String,
    val timesOperated: Int,
    val reputation: String,
    val address: String,
    val action: String
)