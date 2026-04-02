package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.exceptions.StockOrderNotFoundException;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.infrastructure.persistence.repository.StockOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockOrderService {
    private final StockOrderRepository stockOrderRepository;

    public StockOrderService(StockOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    public Long create(StockOrder stockOrder) {
        StockOrder savedStockOrder = stockOrderRepository.save(stockOrder);
        return savedStockOrder.getId();
    }

    public Optional<StockOrder> findById(Long stockOrderId) {
        return stockOrderRepository.findById(stockOrderId);
    }

    public Optional<StockOrder> findByClientId(Long clientId) {
        return stockOrderRepository.findByClientId(clientId);
    }

    public List<StockOrder> findAll() {
        return stockOrderRepository.findAll();
    }

    public void approveOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.approve();
    }

    public void payOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.pay();
    }

    public void completeOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.complete();
    }

    public void cancelOrder(Long stockOrderId) {
        StockOrder stockOrder = stockOrderRepository.findById(stockOrderId)
                .orElseThrow(() -> new StockOrderNotFoundException(stockOrderId));
        stockOrder.cancel();
    }

    public void deleteById(Long stockOrderId) {
        stockOrderRepository.deleteById(stockOrderId);
    }
}