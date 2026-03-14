package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.order.stock.StockOrder;

import java.util.List;

public interface StockOrderRepository {
    StockOrder save(StockOrder stockOrder);

    void delete(Long orderId);

    StockOrder findById(Long orderId);

    List<StockOrder> findAll();
}