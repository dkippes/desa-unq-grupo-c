package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.helpers.Factory
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserAlreadyExistsException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.LoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseUserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository
    override fun registerUser(registerUserDTO: RegisterUserDTO): ResponseUserDTO {
        // TODO: Hashear password
        val user = Factory.createUserFromRequestUserDTO(registerUserDTO)
        if (userRepository.existsByEmail(user.email)) {
            throw UserAlreadyExistsException()
        }
        return Factory.createDTOFromUser(userRepository.save(user))
    }

    override fun login(loginUserDTO : LoginUserDTO): ResponseUserDTO {
        val user = userRepository.findByEmail(loginUserDTO.email)
        if (user == null || user.password != loginUserDTO.password) {
            throw UserNotFoundException()
        }
        return Factory.createDTOFromUser(user)
    }
}