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
    public long AddOrder(AddStockOrderRequest request) {
        long clientId = request.inputDto().clientId();
        long carVersionId = request.inputDto().carVersionId();
        StockOrder stockOrder = new StockOrder(0, clientId, carVersionId);
        stockOrder = stockOrderRepository.save(stockOrder);

        return stockOrder.getOrderId();
    }

    @Override
    public void DeleteOrder(UpdateStockOrderRequest request) {
        long orderId = request.stockOrderId();
        stockOrderRepository.delete(orderId);
    }

    @Override
    public void ApproveOrder(UpdateStockOrderRequest request) {
        long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.approve();
        stockOrderRepository.save(stockOrder);
    }

    @Override
    public void PayOrder(UpdateStockOrderRequest request) {
        long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.pay();
        stockOrderRepository.save(stockOrder);
    }

    @Override
    public void CompleteOrder(UpdateStockOrderRequest request) {
        long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.complete();
        stockOrderRepository.save(stockOrder);
    }

    @Override
    public void CancelOrder(UpdateStockOrderRequest request) {
        long orderId = request.stockOrderId();
        StockOrder stockOrder = stockOrderRepository.findById(orderId);
        stockOrder.cancel();
        stockOrderRepository.save(stockOrder);
    }
}