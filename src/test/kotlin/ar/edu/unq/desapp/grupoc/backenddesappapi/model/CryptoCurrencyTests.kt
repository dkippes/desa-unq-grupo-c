package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.math.BigDecimal
import java.time.LocalDateTime

class CryptoCurrencyTests {

    @Test
    fun shouldShowCryptoCurrencyInfo() {
        val crypto = CryptoCurrency(
            price = BigDecimal.valueOf(100000.0),
            symbol = SYMBOL.BTCUSDT,
            lastUpdateDateAndTime = LocalDateTime.now()
        )

        assertAll( {
            Assertions.assertEquals(crypto.price, BigDecimal.valueOf(100000.0));
            Assertions.assertEquals(crypto.symbol, SYMBOL.BTCUSDT);
            Assertions.assertNotNull(crypto.lastUpdateDateAndTime)
        })
    }
}