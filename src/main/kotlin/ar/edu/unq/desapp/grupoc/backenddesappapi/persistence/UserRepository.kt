package ar.edu.unq.desapp.grupoc.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository {
    fun registerUser(user: User) : User
}