package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.DolarCrypto
import java.math.BigDecimal

interface DollarValueStrategy {
    fun getDollarValue(dolarToday: DolarCrypto): BigDecimal
}