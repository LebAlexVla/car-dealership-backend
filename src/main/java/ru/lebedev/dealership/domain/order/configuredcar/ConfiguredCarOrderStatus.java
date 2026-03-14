package ru.lebedev.dealership.domain.order.configuredcar;

public enum ConfiguredCarOrderStatus {
    PLACED,
    APPROVED,
    WAITING_FOR_PAYMENT,
    PAID,
    AWAITING_DELIVERY,
    READY_FOR_PICK_UP,
    COMPLETED,
    CANCELLED
}
