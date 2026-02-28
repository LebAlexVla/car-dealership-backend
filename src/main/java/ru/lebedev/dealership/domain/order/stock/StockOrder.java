package ru.lebedev.dealership.domain.order.stock;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;

import java.util.UUID;

public class StockOrder {
    private final UUID orderId;
    private final CarVersionId carVersionId;

    private StockOrderStatus status = StockOrderStatus.PLACED;

    public StockOrder(UUID orderId, CarVersionId carVersionId) {
        this.orderId = orderId;
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
            throw new IllegalArgumentException(
                    "Illegal transition from " + status
            );
        }
    }

    public CarVersionId getCarVersionId() {
        return carVersionId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}