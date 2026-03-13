package ru.lebedev.dealership.domain.order.stock;

import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;

public class StockOrder {
    private final long orderId;
    private final long clientId;
    private final long carVersionId;

    private StockOrderStatus status = StockOrderStatus.PLACED;

    public StockOrder(long orderId, long clientId, long carVersionId) {
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

    public long getCarVersionId() {
        return carVersionId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getClientId() {
        return clientId;
    }
}