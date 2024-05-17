package ar.edu.unq.desapp.grupoc.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Account
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.OperationIntent
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto.CryptoCurrencyDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.*
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class Factory {

    companion object {

        fun listDtoToEntity(dtos: List<CryptoCurrencyDTO>): List<CryptoCurrency> {
            return dtos.stream()
                .map { dto: CryptoCurrencyDTO -> dtoToEntity(dto) }.toList()
        }
        private fun dtoToEntity(dto: CryptoCurrencyDTO): CryptoCurrency {
            return CryptoCurrency(dto.symbol, dto.price, LocalDateTime.now())
        }

        fun createUserFromRequestUserDTO(dto: RegisterUserDTO): User {
            return User(
                dto.name!!,
                dto.lastName!!,
                dto.email!!,
                dto.password!!,
                dto.address!!,
                account = Account(
                    cvu = dto.cvu!!,
                    walletAddress = dto.walletAddress!!
                )
            )
        }

        fun createDTOFromUser(user: User): ResponseUserDTO {
            return ResponseUserDTO(
                id = user.id!!,
                name = user.name,
                email = user.email,
                lastName = user.lastName,
                account = AccountDTO(
                    cvu = user.account!!.cvu,
                    walletAddress = user.account!!.walletAddress,
                    reputation = user.account!!.reputation
                ),
                address = user.address
            )
        }

        fun createOperationIntent(intent: ExpressIntentionDTO, user: User,
                                  localQuote: BigDecimal,
                                  cryptoQuote: BigDecimal): OperationIntent {
            return OperationIntent(
                intent.cryptoAsset!!,
                intent.nominalAmount!!,
                cryptoQuote,
                localQuote,
                intent.operationType!!,
                user.account
            )
        }

        fun createExpressIntentionResponseDTO(operationIntent: OperationIntent, user: User): ExpressIntentionResponseDTO {
            return ExpressIntentionResponseDTO(
                operationIntent.symbol,
                operationIntent.nominalQuantity,
                operationIntent.nominalPrice,
                operationIntent.localPrice,
                user.name,
                user.lastName,
                operationIntent.operation
            )
        }

        fun createListCryptoActiveIntentionResponseDTO(user: User, activeIntents: List<OperationIntent>): ListCryptoActiveIntentionResponseDTO {
            return ListCryptoActiveIntentionResponseDTO(
                user.name,
                user.lastName,
                user.account!!.getOperationsReputations(),
                activeIntents.map { intent ->
                    ActiveIntentionResponseDTO(
                        intent.createdDate,
                        intent.symbol,
                        intent.nominalQuantity,
                        intent.nominalPrice,
                        intent.localPrice
                    )
                }
            )
        }
    }
}