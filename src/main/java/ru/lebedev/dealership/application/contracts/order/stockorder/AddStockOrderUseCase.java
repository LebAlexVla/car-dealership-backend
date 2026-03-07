package ru.lebedev.dealership.application.contracts.order.stockorder;

import ru.lebedev.dealership.application.contracts.order.stockorder.operations.AddStockOrder;

public interface AddStockOrderUseCase {
    AddStockOrder.Response add(AddStockOrder.Request request);
}
