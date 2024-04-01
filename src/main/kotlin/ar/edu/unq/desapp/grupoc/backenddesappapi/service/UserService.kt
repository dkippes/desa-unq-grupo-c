package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO

interface UserService {
    fun registerUser(registerUserDTO: RegisterUserDTO) : User
}