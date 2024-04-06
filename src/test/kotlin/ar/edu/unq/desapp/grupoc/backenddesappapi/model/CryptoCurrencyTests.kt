package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class CryptoCurrencyTests {

    @Test
    fun shouldShowCryptoCurrencyInfo() {
        val crypto = CryptoCurrency(
            price = 100000.0,
            symbol = SYMBOL.BTCUSDT
        )

        assertAll( {
            Assertions.assertEquals(crypto.price, 100000.0);
            Assertions.assertEquals(crypto.symbol, SYMBOL.BTCUSDT);
            Assertions.assertNotNull(crypto.lastUpdateDateAndTime)
        })
    }
}