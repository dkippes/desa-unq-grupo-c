package ar.edu.unq.desapp.grupoc.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Account
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.AccountDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseUserDTO
import org.springframework.stereotype.Component

@Component
class Factory {

    companion object {
        //TODO(Builder)
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
    }
}