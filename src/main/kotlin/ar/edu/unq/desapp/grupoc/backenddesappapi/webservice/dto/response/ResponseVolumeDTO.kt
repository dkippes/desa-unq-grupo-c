package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class ResponseVolumeDTO (
    var  date: LocalDateTime = LocalDateTime.now(),
    var  totalOperated: BigDecimal,
    var  localTotalOperated: BigDecimal,
    var operatedCryptos: List<ResponseCryptoStockDTO> = emptyList()
)