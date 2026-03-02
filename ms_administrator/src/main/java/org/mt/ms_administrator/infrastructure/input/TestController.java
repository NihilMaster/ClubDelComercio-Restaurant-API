package org.mt.ms_administrator.infrastructure.input;

import org.mt.ms_administrator.domain.ports.in.GetTestGreetingUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class TestController {

    private final GetTestGreetingUseCase getTestGreetingUseCase;

    public TestController(GetTestGreetingUseCase getTestGreetingUseCase) {
        this.getTestGreetingUseCase = getTestGreetingUseCase;
    }

    @GetMapping("/greeting")
    public Mono<String> greeting() {
        return getTestGreetingUseCase.getGreeting();
    }
}