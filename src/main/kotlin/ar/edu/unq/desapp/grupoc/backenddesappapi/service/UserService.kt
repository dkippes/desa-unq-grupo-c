package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestLoginUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request.RequestRegisterUserDTO
import ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response.ResponseUserDTO

interface UserService {
    fun registerUser(registerUserDTO: RequestRegisterUserDTO) : ResponseUserDTO
    fun login(loginUserDTO : RequestLoginUserDTO) : ResponseUserDTO
}