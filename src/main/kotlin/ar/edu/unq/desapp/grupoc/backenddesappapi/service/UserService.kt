package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.LoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.RegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.ResponseUserDTO

interface UserService {
    fun registerUser(registerUserDTO: RegisterUserDTO) : ResponseUserDTO
    fun login(loginUserDTO : LoginUserDTO) : ResponseUserDTO

    fun clearAll()
}