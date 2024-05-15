package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl.usercase

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.OPERATION
import org.springframework.stereotype.Component

@Component
class DollarStrategyProvider {
    private val strategyMap: Map<OPERATION, DollarValueStrategy> = mapOf(
        OPERATION.BUY to BuyDollarStrategy(),
        OPERATION.SELL to SellDollarStrategy()
    )

    fun getStrategy(operationType: OPERATION): DollarValueStrategy {
        return strategyMap[operationType] ?: throw IllegalArgumentException("Strategy not found for operation: $operationType")
    }
}