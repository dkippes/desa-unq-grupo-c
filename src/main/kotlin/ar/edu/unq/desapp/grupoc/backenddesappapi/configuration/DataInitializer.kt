package ar.edu.unq.desapp.grupoc.backenddesappapi.configuration

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.Account
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.OperationIntent
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OperationStatus
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.CryptoRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.OperationIntentionRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.math.BigDecimal
import java.time.LocalDateTime

@Configuration
class DataInitializer {

    @Bean
    fun initData(
            userRepository: UserRepository,
        cryptocurrencyRepository: CryptoRepository,
        operationIntentRepository: OperationIntentionRepository,
    ) = CommandLineRunner {

        val account1 = Account(cvu = "1234567890123456789034", walletAddress = "FBCDEFGH", reputation = 0)
        val account2 = Account(cvu = "1234567890123456789012", walletAddress = "ABCDEFGH", reputation = 0)

        val passwordEncoder = BCryptPasswordEncoder()

        val password = passwordEncoder.encode("Password123!")

        // Insert users
        val user1 = User(name = "Marie", lastName = "Doe", email = "marie.doe@example.com", password = password, address = "123 Main St", account = account1)
        val user2 = User(name = "John", lastName = "Doe", email = "john.doe@example.com", password = password, address = "123 Main St", account = account2)

        userRepository.saveAll(listOf(user1, user2))

        // Insert cryptocurrency
        val cryptocurrency = CryptoCurrency(symbol = SYMBOL.BTCUSDT, price = BigDecimal(45000.0), lastUpdateDateAndTime = LocalDateTime.now())
        cryptocurrencyRepository.save(cryptocurrency)

        // Insert operation intents
        val operationIntent1 = OperationIntent(symbol = SYMBOL.BTCUSDT, nominalQuantity = BigDecimal(2), nominalPrice = BigDecimal(0.69400), localPrice = BigDecimal(600000.0), operation = OPERATION.BUY, account = account1, status = OperationStatus.OPEN, createdDate = LocalDateTime.now())
        val operationIntent2 = OperationIntent(symbol = SYMBOL.ETHUSDT, nominalQuantity = BigDecimal(5), nominalPrice = BigDecimal(600.0), localPrice = BigDecimal(800000.0), operation = OPERATION.SELL, account = account2, status = OperationStatus.OPEN, createdDate = LocalDateTime.now())
        operationIntentRepository.saveAll(listOf(operationIntent1, operationIntent2))
    }
}