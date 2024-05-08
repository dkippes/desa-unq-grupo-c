package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.OperationIntent
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.OperationIntentionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.IntentService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ActiveIntentionResponseDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ExpressIntentionResponseDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ListCryotoActiveIntentionResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IntentServiceImpl : IntentService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var operationIntentRepository: OperationIntentionRepository

    override fun expressIntentionResponseDTO(intent: ExpressIntentionDTO, userId: Long): ExpressIntentionResponseDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }

        // Buscar cuanto vale la cripto
        val crypoQuote = 0.0

        // Convertir de dolar a pesos
        val localQuote = 0.0

        val operationIntention = OperationIntent(
            intent.cryptoAsset!!,
            intent.nominalAmount!!,
            crypoQuote,
            localQuote,
            intent.operationType!!,
            user.account
        )
        val savedOperationIntent = operationIntentRepository.save(operationIntention)

        return ExpressIntentionResponseDTO(
            savedOperationIntent.symbol,
            savedOperationIntent.nominalQuantity,
            savedOperationIntent.nominalPrice,
            savedOperationIntent.localPrice,
            user.name,
            user.lastName,
            savedOperationIntent.operation
        )
    }

    override fun listActiveIntentionResponseDTO(userId: Long): ListCryotoActiveIntentionResponseDTO? {
        val user = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }

        val activeIntents = operationIntentRepository.findByAccountIdAndStatus(user.id!!, OperationStatus.OPEN)

        return ListCryotoActiveIntentionResponseDTO(
            user.name,
            user.lastName,
            user.getOperationsReputations(),
            activeIntents.map {
                ActiveIntentionResponseDTO(
                    it.createdDate,
                    it.symbol,
                    it.nominalQuantity,
                    it.nominalPrice,
                    it.localPrice
                )
            }
        )
    }

}