package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request

data class RequestCreateTransactionDTO(
    var userId: Long? = null,
    var operationId: Long? = null
)