package org.mt.ms_administrator.infrastructure.input.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private LocalDateTime periodFrom;
    private LocalDateTime periodTo;
    private BigDecimal totalBilling;
    private Long totalOrders;
    private List<TopItemDTO> topItems;

    @Data
    @Builder
    public static class TopItemDTO {
        private Long menuItemId;
        private String menuItemName;
        private Long quantitySold;
        private BigDecimal revenue;
    }
}