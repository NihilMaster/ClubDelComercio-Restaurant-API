package org.mt.ms_administrator.application.usecase;

import org.mt.ms_administrator.domain.ports.in.GetTestGreetingUseCase;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetTestGreetingService implements GetTestGreetingUseCase {
    @Override
    public Mono<String> getGreeting() {
        return Mono.just("¡Hola desde Arquitectura Hexagonal con WebFlux!");
    }
}