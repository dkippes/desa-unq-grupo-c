package ar.edu.unq.desapp.grupoc.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User

interface UserRepository {
    fun registerUser(user: User) : User
}