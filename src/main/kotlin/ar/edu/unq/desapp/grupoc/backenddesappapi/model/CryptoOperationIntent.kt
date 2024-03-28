package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import ar.edu.unq.desapp.grupoc.backenddesappapi.OPERATION
import ar.edu.unq.desapp.grupoc.backenddesappapi.SYMBOLS

class CryptoOperationIntent {
    var id: Long? = null
    var symbol: SYMBOLS? = null
    var nominalQuantity: Double = 0.0
    var nominalPrice: Double = 0.0
    var localPrice: Double = 0.0
    var user: User? = null
    var operation: OPERATION? = null
}