package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrency
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.CryptoCurrencyList
import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import ar.edu.unq.desapp.grupoc.backenddesappapi.persistence.CryptoRepository
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.CryptoService
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions.CryptoCurrencyNotFoundException
import ar.edu.unq.desapp.grupoc.backenddesappapi.service.proxys.BinanceProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CryptoServiceImpl : CryptoService {
    @Autowired
    lateinit var cryptoRepository: CryptoRepository

    @Autowired
    lateinit var binanceProxyService: BinanceProxyService


    override fun getAllCryptoCurrencyPrices(): CryptoCurrencyList {
        val entity: List<CryptoCurrency> = binanceProxyService.getAllCryptoCurrencyValues() as List<CryptoCurrency>
        entity.forEach {
            it.lastUpdateDateAndTime = LocalDateTime.now()
        }
        cryptoRepository.saveAll(entity)
        return CryptoCurrencyList(entity)
    }

    override fun getCryptoCurrencyPrice(symbol: SYMBOL): CryptoCurrency? {
        val entity: CryptoCurrency? = binanceProxyService.getCryptoCurrencyValue(symbol)
        if(entity == null) throw CryptoCurrencyNotFoundException()
        entity.lastUpdateDateAndTime = LocalDateTime.now()
        return cryptoRepository.save(entity)
    }

}