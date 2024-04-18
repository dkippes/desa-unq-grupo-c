package ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions

enum class MSG_EXCEPTIONS {
    USER_NOT_FOUND {
        override fun toString(): String {
            return "User not found"
        }
    },
    USER_ALREADY_EXISTS {
        override fun toString(): String {
            return "User already exists"
        }
    }
}