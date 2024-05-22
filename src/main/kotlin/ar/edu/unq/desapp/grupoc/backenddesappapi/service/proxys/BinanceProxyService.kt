package ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys

import ar.edu.unq.desapp.grupoc.backenddesappapi.helpers.Factory
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto.CryptoCurrencyDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class BinanceProxyService {
    var restTemplate = RestTemplate()
    var objectMapper: ObjectMapper = ObjectMapper()

    @Value("\${integration.binance.api.url:NONE}")
    var binanceApiURL: String? = null

    fun getAllCryptoCurrencyValues(): List<CryptoCurrency>? {
        val responseJson = restTemplate.getForObject(
            binanceApiURL + "ticker/price?symbols=" + SYMBOL.toFormattedList(),
            String::class.java
        )

        val cryptoCurrencies: List<CryptoCurrencyDTO> =
            objectMapper.readValue<List<CryptoCurrencyDTO>>(responseJson ?: "")

        return Factory.listDtoToEntity(cryptoCurrencies)
    }

    fun getCryptoCurrencyValue(symbol: SYMBOL): CryptoCurrency? {
        return restTemplate.getForObject(
            binanceApiURL + "ticker/price?symbol=" + symbol.toString(),
            CryptoCurrency::class.java
        )
    }
}