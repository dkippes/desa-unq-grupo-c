package ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class CryptoCurrencyNotFoundException : EntityNotFoundException(MSG_EXCEPTIONS.CRYPTO_CURRENCY_NOT_FOUND.toString())