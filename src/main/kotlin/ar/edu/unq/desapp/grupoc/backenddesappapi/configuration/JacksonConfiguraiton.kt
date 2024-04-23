package ar.edu.unq.desapp.grupoc.backenddesappapi.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTimeSerializer = LocalDateTimeSerializer(formatter)
        val localDateTimeDeserializer = LocalDateTimeDeserializer(formatter)
        objectMapper.registerModule(JavaTimeModule().addSerializer(LocalDateTime::class.java, localDateTimeSerializer))
        objectMapper.registerModule(JavaTimeModule().addDeserializer(LocalDateTime::class.java, localDateTimeDeserializer))

        return objectMapper
    }
}