package ar.edu.unq.desapp.grupoc.backenddesappapi.model

import java.time.LocalDate

class CryptoDailyRate {
    val date : LocalDate = LocalDate.now()
    val rates : MutableList<CryptoHourRate>? = null
}