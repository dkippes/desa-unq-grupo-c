package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.helpers.Factory
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.OperationIntentionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.IntentService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.DolarCryptoApi
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase.DollarStrategyProvider
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase.QuoteCalculator
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys.BinanceProxyService
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ExpressIntentionDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ExpressIntentionResponseDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ListCryptoActiveIntentionResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IntentServiceImpl : IntentService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var operationIntentRepository: OperationIntentionRepository

    @Autowired
    lateinit var dolarCryptoApi: DolarCryptoApi

    @Autowired
    lateinit var binanceProxyService: BinanceProxyService

    @Autowired
    lateinit var dolarStrategyProvider: DollarStrategyProvider

    override fun expressIntention(intent: ExpressIntentionDTO, userId: Long): ExpressIntentionResponseDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }

        val dolarToday = dolarCryptoApi.getDolarCrypto()
        val crypto = binanceProxyService.getCryptoCurrencyValue(intent.cryptoAsset!!)
        val dolar = dolarStrategyProvider.getStrategy(intent.operationType!!).getDollarValue(dolarToday)
        val localQuote = QuoteCalculator.calculateLocalQuote(crypto, dolar, intent.nominalAmount)
        val cryptoQuote = QuoteCalculator.calculateCryptoQuote(crypto, intent.nominalAmount)

        val operationIntention = Factory.createOperationIntent(intent, user, localQuote, cryptoQuote)
        val savedOperationIntent = operationIntentRepository.save(operationIntention)

        return Factory.createExpressIntentionResponseDTO(savedOperationIntent, user)
    }

    override fun listActiveIntentionResponseDTO(userId: Long): ListCryptoActiveIntentionResponseDTO? {
        val user = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }

        val activeIntents = operationIntentRepository.findByAccountIdAndStatus(user.id!!, OperationStatus.OPEN)

        return Factory.createListCryptoActiveIntentionResponseDTO(user, activeIntents)
    }
}