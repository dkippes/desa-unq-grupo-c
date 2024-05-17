package ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions

import org.apache.coyote.BadRequestException

class PriceChangedOutOfLimitsException() : BadRequestException("Operation has been cancelled because currency price has exceeded operation limits.")