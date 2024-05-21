package ar.edu.unq.desapp.grupoc.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CryptoRepository : JpaRepository<CryptoCurrency, SYMBOL>