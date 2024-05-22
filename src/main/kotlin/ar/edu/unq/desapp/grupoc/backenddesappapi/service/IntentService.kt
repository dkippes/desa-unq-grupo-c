package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseListCryptoActiveIntentionDTO

interface IntentService {
    fun expressIntention(intent: RequestExpressIntentionDTO, userId: Long): ResponseExpressIntentionDTO
    fun listActiveIntentionResponseDTO(userId: Long): ResponseListCryptoActiveIntentionDTO?
}