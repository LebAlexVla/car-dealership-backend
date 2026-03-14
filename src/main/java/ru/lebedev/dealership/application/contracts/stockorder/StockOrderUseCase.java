package ru.lebedev.dealership.application.contracts.stockorder;

import ru.lebedev.dealership.application.contracts.stockorder.requests.AddStockOrderRequest;
import ru.lebedev.dealership.application.contracts.stockorder.requests.UpdateStockOrderRequest;

public interface StockOrderUseCase {
    long AddOrder(AddStockOrderRequest request);

    void DeleteOrder(UpdateStockOrderRequest request);

    void ApproveOrder(UpdateStockOrderRequest request);

    void PayOrder(UpdateStockOrderRequest request);

    void CompleteOrder(UpdateStockOrderRequest request);

    void CancelOrder(UpdateStockOrderRequest request);
}