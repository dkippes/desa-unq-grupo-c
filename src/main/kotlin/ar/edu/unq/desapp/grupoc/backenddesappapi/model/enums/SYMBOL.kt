package ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums

enum class SYMBOL {
    ALICEUSDT,
    MATICUSDT,
    AXSUSDT,
    AAVEUSDT,
    ATOMUSDT,
    NEOUSDT,
    DOTUSDT,
    ETHUSDT,
    CAKEUSDT,
    BTCUSDT,
    BNBUSDT,
    ADAUSDT,
    TRXUSDT,
    AUDIOUSDT;

    companion object {
        fun toFormattedList(): String {
            return "[".plus(entries.joinToString(",") { "\"${it.name}\"" }).plus("]")
        }

        fun valueOfIndex(index: Int): SYMBOL {
            return entries[index]
        }
    }
}