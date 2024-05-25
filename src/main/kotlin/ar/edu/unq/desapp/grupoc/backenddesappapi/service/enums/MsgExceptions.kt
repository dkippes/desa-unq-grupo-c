package ar.edu.unq.desapp.grupoc.backenddesappapi.service.enums

enum class MsgExceptions {
    USER_NOT_FOUND {
        override fun toString(): String {
            return "User not found"
        }
    },
    USER_ALREADY_EXISTS {
        override fun toString(): String {
            return "User already exists"
        }
    },
    CRYPTO_CURRENCY_NOT_FOUND {
        override fun toString(): String {
            return "Crypto currency not found"
        }
    }
}