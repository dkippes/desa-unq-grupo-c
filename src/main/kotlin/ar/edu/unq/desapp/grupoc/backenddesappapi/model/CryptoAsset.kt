package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.SYMBOLS

class CryptoAsset {
    var symbol: SYMBOLS? = null
    var dailyRates: MutableList<CryptoDailyRate>? = null
}