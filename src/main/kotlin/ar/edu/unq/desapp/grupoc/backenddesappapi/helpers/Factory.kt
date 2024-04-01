package ar.edu.unq.desapp.grupoc.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import org.springframework.stereotype.Component

@Component
class Factory {

    companion object {
        fun createUserFromRequestUserDTO(dto: RegisterUserDTO): User {
            return User(
                dto.name,
                dto.lastName,
                dto.email,
                dto.password,
                dto.cvu,
                dto.address,
                dto.walletAddress
            )
        }
    }
}