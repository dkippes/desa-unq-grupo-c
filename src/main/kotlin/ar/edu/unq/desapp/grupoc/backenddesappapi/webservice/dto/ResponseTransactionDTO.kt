package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

class ResponseTransactionDTO(
    val id : Long,
    val status: String,
    val initiatedAt: String,
    val seller: String,
    val buyer: String,
    val intention: String
)