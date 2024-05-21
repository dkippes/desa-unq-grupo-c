package ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions

import org.apache.coyote.BadRequestException

class TransferAlreadySentException : BadRequestException("Transfer has already been sent.")