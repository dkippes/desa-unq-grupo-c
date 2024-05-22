package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.dto.request

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.TransactionStatus
import jakarta.validation.constraints.NotNull

data class RequestProcessDTO(
    @field:NotNull var action: TransactionStatus? = null,
    @field:NotNull val userId: Long? = null
)
