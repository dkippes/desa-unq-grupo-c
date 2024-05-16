package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.client.DolarCrypto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.math.BigDecimal

class DollarStrategyProviderTest {
    private lateinit var dollarStrategyProvider: DollarStrategyProvider

    @BeforeEach
    fun setUp() {
        dollarStrategyProvider = DollarStrategyProvider()
    }

    @Test
    fun `should return BuyDollarStrategy for BUY operation`() {
        // Act
        val strategy = dollarStrategyProvider.getStrategy(OPERATION.BUY)
        val dolar = DolarCrypto(BigDecimal("100"), BigDecimal("120"))

        // Assert
        assertEquals(BuyDollarStrategy::class.java, strategy::class.java)
        assertEquals(strategy.getDollarValue(dolar), BigDecimal("120"))
    }

    @Test
    fun `should return SellDollarStrategy for SELL operation`() {
        // Act
        val strategy = dollarStrategyProvider.getStrategy(OPERATION.SELL)
        val dolar = DolarCrypto(BigDecimal("100"), BigDecimal("120"))

        // Assert
        assertEquals(SellDollarStrategy::class.java, strategy::class.java)
        assertEquals(strategy.getDollarValue(dolar), BigDecimal("100"))
    }

    @Test
    fun `should throw IllegalArgumentException for null operation`() {
        // Arrange
        val invalidOperation = mock(OPERATION::class.java)

        // Act & Assert
        assertThrows(IllegalArgumentException::class.java) {
            dollarStrategyProvider.getStrategy(invalidOperation!!)
        }
    }
}