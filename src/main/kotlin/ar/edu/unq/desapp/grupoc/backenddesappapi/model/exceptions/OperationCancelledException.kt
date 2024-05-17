package ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions

import org.apache.coyote.BadRequestException

class OperationCancelledException() : BadRequestException("Operation has been cancelled")