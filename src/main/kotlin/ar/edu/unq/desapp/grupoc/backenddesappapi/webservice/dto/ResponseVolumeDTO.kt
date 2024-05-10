package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class ResponseVolumeDTO (
    var  datetime: LocalDateTime = LocalDateTime.now(),
    var  totalOperated: Double,
    var  localTotalOperated: Double,
    var cryptos: List<CryptoStockDTO> = emptyList()
)