package org.mt.ms_administrator.infrastructure.input.controller;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.ports.in.GenerateReportUseCase;
import org.mt.ms_administrator.infrastructure.input.dto.response.ReportResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final GenerateReportUseCase reportUseCase;

    @GetMapping("/billing")
    public Mono<ReportResponse> getBillingReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return reportUseCase.getTotalBillingBetween(from, to)
                .flatMap(total ->
                        reportUseCase.getTopSellingItems(from, to, 10)
                                .collectList()
                                .map(items -> ReportResponse.builder()
                                        .periodFrom(from)
                                        .periodTo(to)
                                        .totalBilling(total)
                                        .totalOrders((long) items.size())
                                        .topItems(items.stream()
                                                .map(i -> ReportResponse.TopItemDTO.builder()
                                                        .menuItemId(i.getMenuItemId())
                                                        .menuItemName(i.getMenuItemName())
                                                        .quantitySold(i.getTotalQuantity())
                                                        .revenue(i.getTotalRevenue())
                                                        .build())
                                                .toList())
                                        .build())
                );
    }

    @GetMapping("/billing/summary")
    public Mono<ReportResponse> getBillingSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return reportUseCase.getTotalBillingBetween(from, to)
                .map(total -> ReportResponse.builder()
                        .periodFrom(from)
                        .periodTo(to)
                        .totalBilling(total)
                        .build());
    }
}