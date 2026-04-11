package ru.lebedev.dealership.controller.order.stock.dto;

public record StockOrderOutputDto(
        Long id,
        Long clientId,
        Long carVersionId,
        String status
) {
}