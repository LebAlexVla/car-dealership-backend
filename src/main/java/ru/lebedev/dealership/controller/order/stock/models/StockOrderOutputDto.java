package ru.lebedev.dealership.controller.order.stock.models;

public record StockOrderOutputDto(
        Long id,
        Long clientId,
        Long carVersionId,
        String status
) {
}