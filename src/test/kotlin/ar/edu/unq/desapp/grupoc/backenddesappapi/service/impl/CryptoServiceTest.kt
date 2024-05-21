package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.CryptoRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.CryptoCurrencyNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys.BinanceProxyService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class CryptoServiceTest {
    @InjectMocks
    private lateinit var cryptoService: CryptoServiceImpl

    @Mock
    lateinit var cryptoRepository: CryptoRepository

    @Mock
    lateinit var binanceProxyService: BinanceProxyService

    @Test
    fun `should return all crypto currency prices`() {
        val mockCryptoList = listOf(
            CryptoCurrency(symbol = SYMBOL.BTCUSDT, price = BigDecimal(40000.0), lastUpdateDateAndTime = LocalDateTime.now()),
            CryptoCurrency(symbol = SYMBOL.ETHUSDT, price = BigDecimal(2500.0), lastUpdateDateAndTime = LocalDateTime.now())
        )

        Mockito.`when`(binanceProxyService.getAllCryptoCurrencyValues()).thenReturn(mockCryptoList)
        Mockito.`when`(cryptoRepository.saveAll(any<List<CryptoCurrency>>())).thenReturn(mockCryptoList)

        // Act
        val result = cryptoService.getAllCryptoCurrencyPrices()

        // Assert
        assertEquals(mockCryptoList.size, result.cryptos.size)
        verify(binanceProxyService).getAllCryptoCurrencyValues()
        verify(cryptoRepository).saveAll(mockCryptoList)
    }

    @Test
    fun `should return a specific crypto currency price when the symbol is found`() {
        // Arrange
        val mockCryptoCurrency = CryptoCurrency(
            symbol = SYMBOL.BTCUSDT,
            price = BigDecimal("40000.0"),
            lastUpdateDateAndTime = LocalDateTime.now()
        )

        Mockito.`when`(binanceProxyService.getCryptoCurrencyValue(SYMBOL.BTCUSDT)).thenReturn(mockCryptoCurrency)
        Mockito.`when`(cryptoRepository.save(mockCryptoCurrency)).thenReturn(mockCryptoCurrency)

        // Act
        val result = cryptoService.getCryptoCurrencyPrice(SYMBOL.BTCUSDT)

        // Assert
        assertNotNull(result)
        assertEquals(SYMBOL.BTCUSDT, result?.symbol)
        assertEquals(BigDecimal("40000.0"), result?.price)
        verify(binanceProxyService).getCryptoCurrencyValue(SYMBOL.BTCUSDT)
        verify(cryptoRepository).save(mockCryptoCurrency)
    }

    @Test
    fun `should throw CryptoCurrencyNotFoundException when the symbol is not found`() {
        // Arrange
        Mockito.`when`(binanceProxyService.getCryptoCurrencyValue(SYMBOL.BTCUSDT)).thenReturn(null)

        assertThrows<CryptoCurrencyNotFoundException> {
            cryptoService.getCryptoCurrencyPrice(SYMBOL.BTCUSDT)
        }
    }
}