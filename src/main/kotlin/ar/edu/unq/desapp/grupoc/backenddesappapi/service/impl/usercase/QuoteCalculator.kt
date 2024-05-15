package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import java.math.BigDecimal
import java.math.RoundingMode

class QuoteCalculator {
    companion object {
        fun calculateLocalQuote(crypto: CryptoCurrency?, dollarRate: BigDecimal, nominalAmount: BigDecimal?): BigDecimal {
            return roundQuote(crypto?.price?.times(dollarRate)?.times(nominalAmount!!))
        }

        fun calculateCryptoQuote(crypto: CryptoCurrency?, nominalAmount: BigDecimal?): BigDecimal {
            return roundQuote(crypto?.price?.times(nominalAmount!!))
        }

        private fun roundQuote(value: BigDecimal?): BigDecimal {
            return value?.setScale(2, RoundingMode.HALF_UP)!!
        }
    }
}