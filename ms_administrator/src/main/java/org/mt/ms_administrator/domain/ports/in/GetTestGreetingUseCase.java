package org.mt.ms_administrator.domain.ports.in;

import reactor.core.publisher.Mono;

public interface GetTestGreetingUseCase {
    Mono<String> getGreeting();
}