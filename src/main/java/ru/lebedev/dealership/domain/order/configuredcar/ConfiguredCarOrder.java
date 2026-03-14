package ru.lebedev.dealership.domain.order.configuredcar;

import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

public class ConfiguredCarOrder {
    private final Long orderId;
    private final Long clientId;
    private final Long carVersionId;
    private final Long configurationId;

    private ConfiguredCarOrderStatus status = ConfiguredCarOrderStatus.PLACED;

    public ConfiguredCarOrder(Long orderId, Long clientId, Long carVersionId, Long configurationId) {
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


    public Long getOrderId() {
        return orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getCarVersionId() {
        return carVersionId;
    }

    public Long getConfiguration() {
        return configurationId;
    }
}