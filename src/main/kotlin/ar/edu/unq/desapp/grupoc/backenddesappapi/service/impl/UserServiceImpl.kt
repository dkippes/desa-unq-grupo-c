package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.helpers.Factory
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.UserService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto.UserDetailsDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserAlreadyExistsException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestLoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestRegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseUserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    @Autowired
    private lateinit var customMetricsService: CustomMetricsService


    override fun registerUser(registerUserDTO: RequestRegisterUserDTO): ResponseUserDTO {
        val user = Factory.createUserFromRequestUserDTO(registerUserDTO)
        if (userRepository.existsByEmail(user.email)) {
            throw UserAlreadyExistsException()
        }

        user.password = passwordEncoder.encode(user.password)

        return Factory.createDTOFromUser(userRepository.save(user))
    }

    override fun login(loginUserDTO : RequestLoginUserDTO): ResponseUserDTO {
        val user = userRepository.findByEmail(loginUserDTO.email!!)
        if (user == null || !passwordEncoder.matches(loginUserDTO.password, user.password)) {
            customMetricsService.incrementLoginFailures()
            throw UserNotFoundException()
        }
        customMetricsService.incrementLoginSuccess()
        return Factory.createDTOFromUser(user)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val response = userRepository.findByEmail(username!!) ?: throw UserNotFoundException()

        return UserDetailsDTO(response)
    }


}

