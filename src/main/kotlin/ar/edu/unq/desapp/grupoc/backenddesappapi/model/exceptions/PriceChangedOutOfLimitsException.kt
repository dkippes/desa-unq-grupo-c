package ar.edu.unq.desapp.grupoc.backenddesappapi.model.exceptions

class PriceChangedOutOfLimitsException() : RuntimeException("Operation has been cancelled because currency price has exceeded operation limits.")