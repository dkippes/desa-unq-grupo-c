package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoc.backenddesappapi.model.enums.SYMBOL
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class CustomMetricsServiceImpl(meterRegistry: MeterRegistry) {

    private val symbolCounters: Map<SYMBOL, Counter> = SYMBOL.entries.associateWith { symbol ->
        Counter.builder("custom_metric_symbol")
            .description("Custom metric for symbol ${symbol.name}")
            .tags("symbol", symbol.name)
            .register(meterRegistry)
    }
    private val loginSuccessCounter = Counter.builder("counter.login.success")
        .description("Custom metric for login success")
        .register(meterRegistry)
    private val loginFailuresCounter = Counter.builder("counter.login.failures")
        .description("Custom metric for login success")
        .register(meterRegistry)

    fun incrementCustomMetric(enumValue: SYMBOL) {
        symbolCounters[enumValue]?.increment()
    }

    fun incrementLoginSuccess() {
        loginSuccessCounter.increment()
    }

    fun incrementLoginFailures() {
        loginFailuresCounter.increment()
    }
}