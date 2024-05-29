package ar.edu.unq.desapp.grupoc.backenddesappapi.service.exceptions

import ar.edu.unq.desapp.grupoc.backenddesappapi.service.enums.MsgExceptions
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserAlreadyExistsException : BadRequestException() {
    override val message: String = MsgExceptions.USER_ALREADY_EXISTS.toString()
}