package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class CustomMetricsService(meterRegistry: MeterRegistry) {

    private val counters: Map<SYMBOL, Counter> = SYMBOL.entries.associateWith { symbol ->
        Counter.builder("custom_metric_symbol")
            .description("Custom metric for symbol ${symbol.name}")
            .tags("symbol", symbol.name)
            .register(meterRegistry)
    }

    fun incrementCustomMetric(enumValue: SYMBOL) {
        counters[enumValue]?.increment()
    }
}