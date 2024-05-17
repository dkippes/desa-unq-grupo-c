package ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions

import org.apache.coyote.BadRequestException

class OperationFinishedException : BadRequestException("Operation has already finished") {
}