package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.order.stock.StockOrder;

@Repository
public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
}