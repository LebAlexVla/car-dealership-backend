package ru.lebedev.dealership.domain.order.configuration;

import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

public class ConfigurationOrder {
    private final long orderId;
    private final long clientId;
    private final long carVersionId;
    private final long configurationId;

    private ConfigurationOrderStatus status = ConfigurationOrderStatus.PLACED;

    public ConfigurationOrder(long orderId, long clientId, long carVersionId, long configurationId) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.carVersionId = carVersionId;
        this.configurationId = configurationId;
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
            throw new OrderStatusTransitionException(
                    "Illegal transition from " + status
            );
        }
    }


    public long getOrderId() {
        return orderId;
    }

    public long getClientId() {
        return clientId;
    }

    public long getCarVersionId() {
        return carVersionId;
    }

    public long getConfiguration() {
        return configurationId;
    }
}