package ru.lebedev.dealership.domain.order.stock;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;
import ru.lebedev.dealership.domain.user.User;

@Entity
@Table(name = "stock_order")
public class StockOrder extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_version_id", nullable = false)
    private CarVersion carVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private StockOrderStatus status = StockOrderStatus.PLACED;

    protected StockOrder() {}

    public StockOrder(User client, CarVersion carVersion) {
        this.client = client;
        this.carVersion = carVersion;
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

    public CarVersion getCarVersion() {
        return carVersion;
    }

    public User getClient() {
        return client;
    }

    public StockOrderStatus getStatus() {
        return status;
    }
}