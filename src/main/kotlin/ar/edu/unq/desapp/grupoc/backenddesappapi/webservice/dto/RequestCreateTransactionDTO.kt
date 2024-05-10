package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

data class RequestCreateTransactionDTO(
    var accountId: Long? = null,
    var operationId: Long? = null
)