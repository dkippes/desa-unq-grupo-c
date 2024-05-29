package ar.edu.unq.desapp.grupoc.backenddesappapi.webservice.handlers

import jakarta.persistence.EntityNotFoundException
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        const val GENERIC_ERROR_MESSAGE = "An unexpected error occurred"
        const val VALIDATION_ERROR_MESSAGE = "Validation failed"
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> {
        val errors = HashMap<String, String>()
        ex.bindingResult.fieldErrors.forEach { error ->
            val fieldName = error.field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: VALIDATION_ERROR_MESSAGE
        }
        return errors
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["details"] = ex.message ?: GENERIC_ERROR_MESSAGE
        return errors
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(ex: EntityNotFoundException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["details"] = ex.message ?: GENERIC_ERROR_MESSAGE
        return errors
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): Map<String, String> {
        val errors = HashMap<String, String>()
        errors["details"] = ex.message ?: GENERIC_ERROR_MESSAGE
        return errors
    }
}