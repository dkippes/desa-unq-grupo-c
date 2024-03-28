package ar.edu.unq.desapp.grupoc.backenddesappapi.model

class CryptoAsset {
    var symbol: SYMBOLS? = null
    var dailyRates: MutableList<CryptoDailyRate>? = null
}