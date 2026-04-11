package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.order.stock.StockOrder;

import java.util.Optional;

@Repository
public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
    Optional<StockOrder> findByClientId(Long clientId);
}