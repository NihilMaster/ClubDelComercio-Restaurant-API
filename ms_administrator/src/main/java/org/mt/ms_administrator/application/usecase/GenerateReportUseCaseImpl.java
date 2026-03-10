package org.mt.ms_administrator.application.usecase;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.ItemSalesCount;
import org.mt.ms_administrator.domain.model.OrderSummary;
import org.mt.ms_administrator.domain.ports.in.GenerateReportUseCase;
import org.mt.ms_administrator.domain.ports.out.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GenerateReportUseCaseImpl implements GenerateReportUseCase {

    private final OrderRepository orderRepository;

    @Override
    public Flux<OrderSummary> getOrdersBetween(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findClosedOrdersBetween(from, to);
    }

    @Override
    public Mono<BigDecimal> getTotalBillingBetween(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findTotalBillingBetween(from, to);
    }

    @Override
    public Flux<ItemSalesCount> getTopSellingItems(LocalDateTime from, LocalDateTime to, int limit) {
        return orderRepository.findTopSellingItems(from, to, limit);
    }
}