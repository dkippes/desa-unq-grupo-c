package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

data class ResponseUserDTO(
                           var id: Long,
                           var name: String,
                           var lastName: String,
                           var email: String,
                           var address: String,
                           var account: AccountDTO)