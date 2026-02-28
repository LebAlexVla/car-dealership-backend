package ru.lebedev.dealership.domain.order.configuration;

import java.util.UUID;

public class ConfigurationOrder {
    private final UUID orderId;

    private ConfigurationOrderStatus status = ConfigurationOrderStatus.PLACED;

    public ConfigurationOrder(UUID orderId) {
        this.orderId = orderId;
    }

    public void approve() {
        checkStatus(ConfigurationOrderStatus.PLACED);
        status = ConfigurationOrderStatus.APPROVED;
        status = ConfigurationOrderStatus.WAITING_FOR_PAYMENT;
    }

    public void pay() {
        checkStatus(ConfigurationOrderStatus.WAITING_FOR_PAYMENT);
        status = ConfigurationOrderStatus.PAID;
        status = ConfigurationOrderStatus.AWAITING_DELIVERY;
    }

    public void deliver() {
        checkStatus(ConfigurationOrderStatus.AWAITING_DELIVERY);
        status = ConfigurationOrderStatus.READY_FOR_PICK_UP;
    }

    public void complete() {
        checkStatus(ConfigurationOrderStatus.READY_FOR_PICK_UP);
        status = ConfigurationOrderStatus.COMPLETED;
    }

    public void cancel() {
        status = ConfigurationOrderStatus.CANCELLED;
    }

    private void checkStatus(ConfigurationOrderStatus expected) {
        if (this.status != expected) {
            throw new IllegalArgumentException(
                    "Illegal transition from " + status
            );
        }
    }


    public UUID getOrderId() {
        return orderId;
    }
}