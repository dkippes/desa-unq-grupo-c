package ar.edu.unq.desapp.grupoc.backenddesappapi.service.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DolarCryptoApi {

    var restTemplate = RestTemplate()
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Value("\${integration.dolar.api.url}")
    var dolarApiUrl: String? = null

    fun getDolarCrypto(): DolarCrypto {
        val responseJson = restTemplate.getForObject(
            dolarApiUrl!!,
            String::class.java
        )

        return objectMapper.readValue<DolarCrypto>(responseJson ?: "")
    }
}