package ar.edu.unq.desapp.grupoc.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import org.springframework.stereotype.Component

@Component
class Factory {

    companion object {
        //TODO(Builder)
        fun createUserFromRequestUserDTO(dto: RegisterUserDTO): User {
            return User(
                dto.name,
                dto.lastName,
                dto.email,
                dto.password,
                dto.address,
            )
        }
    }
}