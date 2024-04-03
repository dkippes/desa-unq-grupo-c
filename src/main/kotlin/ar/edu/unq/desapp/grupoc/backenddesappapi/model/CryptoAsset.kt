package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL

class CryptoAsset {
    var symbol: SYMBOL? = null
    var dailyRates: MutableList<CryptoDailyRate>? = null
}