package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.DolarCrypto
import java.math.BigDecimal

class SellDollarStrategy : DollarValueStrategy {
    override fun getDollarValue(dolarToday: DolarCrypto): BigDecimal = dolarToday.sell
}