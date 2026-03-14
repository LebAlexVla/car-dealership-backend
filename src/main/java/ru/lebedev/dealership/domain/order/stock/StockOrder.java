package ru.lebedev.dealership.domain.order.stock;

import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

public class StockOrder {
    private final Long orderId;
    private final Long clientId;
    private final Long carVersionId;

    private StockOrderStatus status = StockOrderStatus.PLACED;

    public StockOrder(Long orderId, Long clientId, Long carVersionId) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.carVersionId = carVersionId;
    }

    public void approve() {
        checkStatus(StockOrderStatus.PLACED);
        status = StockOrderStatus.APPROVED;
        status = StockOrderStatus.WAITING_FOR_PAYMENT;
    }

    public void pay() {
        checkStatus(StockOrderStatus.WAITING_FOR_PAYMENT);
        status = StockOrderStatus.PAID;
        status = StockOrderStatus.READY_FOR_PICK_UP;
    }

    public void complete() {
        checkStatus(StockOrderStatus.READY_FOR_PICK_UP);
        status = StockOrderStatus.COMPLETED;
    }

    public void cancel() {
        status = StockOrderStatus.CANCELLED;
    }

    private void checkStatus(StockOrderStatus expected) {
        if (this.status != expected) {
            throw new OrderStatusTransitionException(
                    "Illegal transition from " + status
            );
        }
    }

    public Long getCarVersionId() {
        return carVersionId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getClientId() {
        return clientId;
    }
}