package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class QuoteCalculatorTest {

    @Test
    fun emptyClassTest() {
        val quoteCalculator = QuoteCalculator()
        assertNotNull(quoteCalculator)
    }

    @Test
    fun `should correctly calculate local quote based on given crypto price, dollar rate, and nominal amount`() {
        val crypto = CryptoCurrency(SYMBOL.BTCUSDT, BigDecimal("50000"), null)  // Assume price is in USD
        val dollarRate = BigDecimal("200")  // Local currency rate, e.g., ARS to USD
        val nominalAmount = BigDecimal("5")  // Amount of crypto

        val result = QuoteCalculator.calculateLocalQuote(crypto, dollarRate, nominalAmount)

        val expected = BigDecimal("50000000.00")  // Expected result after calculation and rounding
        assertEquals(expected, result)
    }

    @Test
    fun `should correctly calculate crypto quote based on given crypto price and nominal amount`() {
        val crypto = CryptoCurrency(SYMBOL.BTCUSDT, BigDecimal("2000"), null)  // Assume price is in USD
        val nominalAmount = BigDecimal("3")  // Amount of crypto

        val result = QuoteCalculator.calculateCryptoQuote(crypto, nominalAmount)

        val expected = BigDecimal("6000.00")  // Expected result after calculation and rounding
        assertEquals(expected, result)
    }

    @Test
    fun `should return rounded value to two decimal places`() {
        val value = BigDecimal("123.4567")

        val roundedValue = QuoteCalculator.roundQuote(value)

        val expected = BigDecimal("123.46")
        assertEquals(expected, roundedValue)
    }

    @Test
    fun `should handle null values in calculateLocalQuote gracefully`() {
        try {
            QuoteCalculator.calculateLocalQuote(null, BigDecimal("100"), BigDecimal("2"))
        } catch (e: Exception) {
            assertEquals("NullPointerException", e::class.simpleName)
        }
    }

    @Test
    fun `should handle null values in calculateCryptoQuote gracefully`() {
        try {
            QuoteCalculator.calculateCryptoQuote(null, BigDecimal("2"))
        } catch (e: Exception) {
            assertEquals("NullPointerException", e::class.simpleName)
        }
    }
}