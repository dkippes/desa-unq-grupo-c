package ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class BinanceProxyService {
    private val restTemplate = RestTemplate()

    @Value("\${integration.binance.api.url:NONE}")
    private val binanceApiURL: String? = null

    fun getAllCryptoCurrencyValues(): List<*>? {
        val responseJson = restTemplate.getForObject(
            binanceApiURL + "ticker/price?symbols=" + SYMBOL.toFormattedList(),
            String::class.java
        )

        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        // TODO: refactorear en su propio component ObjectMapper
        val cryptoCurrencies: List<CryptoCurrency> = objectMapper.readValue(responseJson ?: "")

        return cryptoCurrencies
    }

    fun geCryptoCurrencyValue(symbol: SYMBOL): CryptoCurrency? {
        val entity = restTemplate.getForObject(
            binanceApiURL + "ticker/price?symbol=" + symbol.toString(),
            CryptoCurrency::class.java
        )
        return entity
    }

}