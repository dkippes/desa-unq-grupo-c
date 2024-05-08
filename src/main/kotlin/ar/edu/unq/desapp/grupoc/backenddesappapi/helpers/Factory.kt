package ar.edu.unq.desapp.grupoc.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Account
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto.CryptoCurrencyDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.AccountDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseUserDTO
import org.springframework.stereotype.Component
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
                    reputation = user.account!!.reputation!!
                ),
                address = user.address
            )
        }
    }
}