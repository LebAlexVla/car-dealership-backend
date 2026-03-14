package ru.lebedev.dealership.application.contracts.order.stock;

import ru.lebedev.dealership.application.contracts.order.stock.requests.AddStockOrderRequest;
import ru.lebedev.dealership.application.contracts.order.stock.requests.UpdateStockOrderRequest;

public interface StockOrderService {
    Long addOrder(AddStockOrderRequest request);

    void deleteOrder(UpdateStockOrderRequest request);

    void approveOrder(UpdateStockOrderRequest request);

    void payOrder(UpdateStockOrderRequest request);

    void completeOrder(UpdateStockOrderRequest request);

    void cancelOrder(UpdateStockOrderRequest request);
}