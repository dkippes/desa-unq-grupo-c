package ar.edu.unq.desapp.grupoc.backenddesappapi.service.impl

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.ReactiveHealthIndicator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class DownstreamServiceHealthIndicator : ReactiveHealthIndicator {
    override fun health(): Mono<Health> {
        return checkDownstreamServiceHealth().onErrorResume { ex -> Mono.just(Health.Builder().down(ex).build()) }
    }

    private fun checkDownstreamServiceHealth(): Mono<Health> {
        // we could use WebClient to check health reactively
        return Mono.just(Health.Builder().up().build())
    }
}