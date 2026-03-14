package ru.lebedev.dealership.application.contracts.stockorder.requests;

import ru.lebedev.dealership.application.contracts.stockorder.models.StockOrderInputDto;

public record AddStockOrderRequest(StockOrderInputDto inputDto) {
}
