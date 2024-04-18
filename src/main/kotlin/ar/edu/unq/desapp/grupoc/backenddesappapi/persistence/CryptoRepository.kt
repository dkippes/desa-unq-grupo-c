package ar.edu.unq.desapp.grupoc.backenddesappapi.persistence

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CryptoRepository : CrudRepository<CryptoCurrency, SYMBOL>