package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.StockOrderRepository;
import ru.lebedev.dealership.application.contracts.order.stock.requests.AddStockOrderRequest;
import ru.lebedev.dealership.application.contracts.order.stock.requests.UpdateStockOrderRequest;
import ru.lebedev.dealership.domain.order.stock.StockOrder;

public class StockOrderServiceImpl implements ru.lebedev.dealership.application.contracts.order.stock.StockOrderService {
    private final StockOrderRepository stockOrderRepository;

    public StockOrderServiceImpl(StockOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    @Override
    public Long addOrder(AddStockOrderRequest request) {
        Long clientId = request.inputDto().clientId();
        Long carVersionId = request.inputDto().carVersionId();
        StockOrder stockOrder = new StockOrder(0L, clientId, carVersionId);
        stockOrder = stockOrderRepository.save(stockOrder);

        return stockOrder.getOrderId();
    }

    @Override
    public void deleteOrder(UpdateStockOrderRequest request) {
        Long orderId = request.stockOrderId();
        stockOrderRepository.delete(orderId);
    }

    @Override
    public void approveOrder(UpdateStockOrderRequest request) {
        Long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.approve();
        stockOrderRepository.save(stockOrder);
    }

    @Override
    public void payOrder(UpdateStockOrderRequest request) {
        Long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.pay();
        stockOrderRepository.save(stockOrder);
    }

    @Override
    public void completeOrder(UpdateStockOrderRequest request) {
        Long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.complete();
        stockOrderRepository.save(stockOrder);
    }

    @Override
    public void cancelOrder(UpdateStockOrderRequest request) {
        Long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.cancel();
        stockOrderRepository.save(stockOrder);
    }
}