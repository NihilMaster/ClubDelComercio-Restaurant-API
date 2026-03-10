package org.mt.ms_administrator.infrastructure.output.repository;

import lombok.RequiredArgsConstructor;
import org.mt.ms_administrator.domain.model.ItemSalesCount;
import org.mt.ms_administrator.domain.model.OrderSummary;
import org.mt.ms_administrator.domain.ports.out.OrderRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class R2dbcOrderRepository implements OrderRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Flux<OrderSummary> findClosedOrdersBetween(LocalDateTime from, LocalDateTime to) {
        String sql = """
            SELECT id, id_table, customer_name, total_cost, created_at, updated_at as closed_at
            FROM restaurante.orders
            WHERE open = false
              AND created_at BETWEEN :from AND :to
            ORDER BY created_at DESC
            """;

        return databaseClient.sql(sql)
                .bind("from", from)
                .bind("to", to)
                .map((row, metadata) -> OrderSummary.builder()
                        .id(row.get("id", Long.class))
                        .tableNumber(row.get("id_table", Long.class))
                        .customerName(row.get("customer_name", String.class))
                        .totalCost(row.get("total_cost", BigDecimal.class))
                        .createdAt(row.get("created_at", LocalDateTime.class))
                        .closedAt(row.get("closed_at", LocalDateTime.class))
                        .build())
                .all();
    }

    @Override
    public Mono<BigDecimal> findTotalBillingBetween(LocalDateTime from, LocalDateTime to) {
        String sql = """
            SELECT COALESCE(SUM(total_cost), 0) as total
            FROM restaurante.orders
            WHERE open = false
              AND created_at BETWEEN :from AND :to
            """;

        return databaseClient.sql(sql)
                .bind("from", from)
                .bind("to", to)
                .map((row, metadata) -> row.get("total", BigDecimal.class))
                .one();
    }

    @Override
    public Flux<ItemSalesCount> findTopSellingItems(LocalDateTime from, LocalDateTime to, int limit) {
        String sql = """
            SELECT 
                mi.id as menu_item_id,
                mi.name as menu_item_name,
                SUM(oi.quantity) as total_quantity,
                SUM(oi.subtotal) as total_revenue
            FROM restaurante.order_items oi
            JOIN restaurante.menu_items mi ON oi.menu_item_id = mi.id
            JOIN restaurante.orders o ON oi.order_id = o.id
            WHERE o.open = false
              AND o.created_at BETWEEN :from AND :to
            GROUP BY mi.id, mi.name
            ORDER BY total_quantity DESC
            LIMIT :limit
            """;

        return databaseClient.sql(sql)
                .bind("from", from)
                .bind("to", to)
                .bind("limit", limit)
                .map((row, metadata) -> ItemSalesCount.builder()
                        .menuItemId(row.get("menu_item_id", Long.class))
                        .menuItemName(row.get("menu_item_name", String.class))
                        .totalQuantity(row.get("total_quantity", Long.class))
                        .totalRevenue(row.get("total_revenue", BigDecimal.class))
                        .build())
                .all();
    }
}