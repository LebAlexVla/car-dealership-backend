package ru.lebedev.dealership.application.contracts.order.stock.requests;

import ru.lebedev.dealership.application.contracts.order.stock.models.StockOrderInputDto;

public record AddStockOrderRequest(StockOrderInputDto inputDto) {
}
