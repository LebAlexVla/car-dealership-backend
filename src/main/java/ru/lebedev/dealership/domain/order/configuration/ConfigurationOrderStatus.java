package ru.lebedev.dealership.domain.order.configuration;

public enum ConfigurationOrderStatus {
    PLACED,
    APPROVED,
    WAITING_FOR_PAYMENT,
    PAID,
    AWAITING_DELIVERY,
    READY_FOR_PICK_UP,
    COMPLETED,
    CANCELLED
}
