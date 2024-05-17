package ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions

import org.apache.coyote.BadRequestException

class TransferNotSentException() : BadRequestException("Transfer has not been sent yet.")