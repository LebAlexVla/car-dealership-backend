package ru.lebedev.dealership.domain.order.configuredcar;

import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

public class ConfiguredCarOrder {
    private final long orderId;
    private final long clientId;
    private final long carVersionId;
    private final long configurationId;

    private ConfiguredCarOrderStatus status = ConfiguredCarOrderStatus.PLACED;

    public ConfiguredCarOrder(long orderId, long clientId, long carVersionId, long configurationId) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.carVersionId = carVersionId;
        this.configurationId = configurationId;
    }

    public void approve() {
        checkStatus(ConfiguredCarOrderStatus.PLACED);
        status = ConfiguredCarOrderStatus.APPROVED;
        status = ConfiguredCarOrderStatus.WAITING_FOR_PAYMENT;
    }

    public void pay() {
        checkStatus(ConfiguredCarOrderStatus.WAITING_FOR_PAYMENT);
        status = ConfiguredCarOrderStatus.PAID;
        status = ConfiguredCarOrderStatus.AWAITING_DELIVERY;
    }

    public void deliver() {
        checkStatus(ConfiguredCarOrderStatus.AWAITING_DELIVERY);
        status = ConfiguredCarOrderStatus.READY_FOR_PICK_UP;
    }

    public void complete() {
        checkStatus(ConfiguredCarOrderStatus.READY_FOR_PICK_UP);
        status = ConfiguredCarOrderStatus.COMPLETED;
    }

    public void cancel() {
        status = ConfiguredCarOrderStatus.CANCELLED;
    }

    private void checkStatus(ConfiguredCarOrderStatus expected) {
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