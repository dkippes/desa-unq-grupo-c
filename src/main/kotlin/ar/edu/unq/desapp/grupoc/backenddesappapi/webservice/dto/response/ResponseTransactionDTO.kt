package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response

import java.math.BigDecimal

class ResponseTransactionDTO(
    val id : Long,
    val price: BigDecimal,
    val amount: BigDecimal,
    val total: BigDecimal,
    val fullName: String,
    val timesOperated: Int,
    val reputation: String,
    val address: String,
    val action: String
)