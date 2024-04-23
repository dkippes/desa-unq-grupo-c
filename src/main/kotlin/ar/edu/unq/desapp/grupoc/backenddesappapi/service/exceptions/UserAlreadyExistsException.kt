package ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions

import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserAlreadyExistsException : BadRequestException() {
    override val message: String = MSG_EXCEPTIONS.USER_ALREADY_EXISTS.toString()
}