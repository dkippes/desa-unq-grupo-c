package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ExpressIntentionResponseDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ListCryptoActiveIntentionResponseDTO

interface IntentService {
    fun expressIntention(intent: ExpressIntentionDTO, userId: Long): ExpressIntentionResponseDTO
    fun listActiveIntentionResponseDTO(userId: Long): ListCryptoActiveIntentionResponseDTO?
}