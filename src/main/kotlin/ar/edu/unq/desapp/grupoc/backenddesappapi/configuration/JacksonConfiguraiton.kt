package ar.edu.unq.desapp.grupoc.backenddesappapi.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        // Formateadores para LocalDateTime
        val localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTimeSerializer = LocalDateTimeSerializer(localDateTimeFormatter)
        val localDateTimeDeserializer = LocalDateTimeDeserializer(localDateTimeFormatter)

        // Formateadores para LocalDate
        val localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDateSerializer = LocalDateSerializer(localDateFormatter)
        val localDateDeserializer = LocalDateDeserializer(localDateFormatter)

        // Registro de módulo JSR310 con formateadores personalizados
        val jsr310Module = JavaTimeModule()
            .addSerializer(LocalDateTime::class.java, localDateTimeSerializer)
            .addDeserializer(LocalDateTime::class.java, localDateTimeDeserializer)
            .addSerializer(LocalDate::class.java, localDateSerializer)
            .addDeserializer(LocalDate::class.java, localDateDeserializer)

        // Configurar ObjectMapper
        objectMapper.registerModule(jsr310Module)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        objectMapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return objectMapper
    }
}