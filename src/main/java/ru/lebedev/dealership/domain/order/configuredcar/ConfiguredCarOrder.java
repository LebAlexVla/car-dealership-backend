package ru.lebedev.dealership.domain.order.configuredcar;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.exceptions.OrderStatusTransitionException;
import ru.lebedev.dealership.domain.user.User;

@Entity
@Table(name = "configured_car_order")
public class ConfiguredCarOrder extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_configuration_id", nullable = false)
    private CarConfiguration configuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfiguredCarOrderStatus status = ConfiguredCarOrderStatus.PLACED;

    protected ConfiguredCarOrder() {
    }

    public ConfiguredCarOrder(CarConfiguration configuration) {
        this.configuration = configuration;
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

    public User getClient() {
        return configuration.getClient();
    }

    public CarVersion getCarVersion() {
        return configuration.getCarVersion();
    }

    public CarConfiguration getConfiguration() {
        return configuration;
    }

    public ConfiguredCarOrderStatus getStatus() {
        return status;
    }
}