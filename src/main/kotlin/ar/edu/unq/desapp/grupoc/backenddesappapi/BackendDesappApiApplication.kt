package ar.edu.unq.desapp.grupoc.backenddesappapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
class BackendDesappApiApplication

fun main(args: Array<String>) {
    runApplication<BackendDesappApiApplication>(*args)
}
