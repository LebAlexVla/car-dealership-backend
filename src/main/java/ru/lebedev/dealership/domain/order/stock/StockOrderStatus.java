package ru.lebedev.dealership.domain.order.stock;

public enum StockOrderStatus {
    PLACED,
    APPROVED,
    WAITING_FOR_PAYMENT,
    PAID,
    READY_FOR_PICK_UP,
    COMPLETED,
    CANCELLED
}