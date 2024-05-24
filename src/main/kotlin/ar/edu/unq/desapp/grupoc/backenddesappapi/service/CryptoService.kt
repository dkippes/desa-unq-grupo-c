package ar.edu.unq.desapp.grupoc.backenddesappapi.service

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.CurrentCryptoQuotePrice
import org.springframework.stereotype.Service

@Service
interface CryptoService {
    fun getAllCryptoCurrencyPrices(): CryptoCurrencyList
    fun getCryptoCurrencyPrice(symbol: SYMBOL): CryptoCurrency?
    fun getHourlyQuotes(symbol: String?): List<CurrentCryptoQuotePrice>
}