package ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.dto.CryptoCurrencyDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime


@Service
class BinanceProxyService {
    private val restTemplate = RestTemplate()
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Value("\${integration.binance.api.url:NONE}")
    private val binanceApiURL: String? = null

    fun getAllCryptoCurrencyValues(): List<*>? {
        val responseJson = restTemplate.getForObject(
            binanceApiURL + "ticker/price?symbols=" + SYMBOL.toFormattedList(),
            String::class.java
        )

        // TODO: refactorear en su propio component ObjectMapper
        val cryptoCurrencies: List<CryptoCurrencyDTO> =
            objectMapper.readValue<List<CryptoCurrencyDTO>>(responseJson ?: "")

        return listDtoToEntity(cryptoCurrencies)
    }

    fun geCryptoCurrencyValue(symbol: SYMBOL): CryptoCurrency? {
        val entity = restTemplate.getForObject(
            binanceApiURL + "ticker/price?symbol=" + symbol.toString(),
            CryptoCurrency::class.java
        )
        return entity
    }

    // TODO: Pasar a una clase de mapper DTO to Entity
    fun listDtoToEntity(dtos: List<CryptoCurrencyDTO>): List<CryptoCurrency> {
        return dtos.stream()
            .map { dto: CryptoCurrencyDTO -> dtoToEntity(dto) }.toList()
    }

    // TODO: Pasar a una clase de mapper DTO to Entity
    fun dtoToEntity(dto: CryptoCurrencyDTO): CryptoCurrency {
        return CryptoCurrency(dto.symbol, dto.price, LocalDateTime.now())
    }
}