package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebedev.dealership.domain.order.stock.StockOrder;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
}