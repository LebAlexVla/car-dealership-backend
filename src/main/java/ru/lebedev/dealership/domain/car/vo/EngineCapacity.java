package ru.lebedev.dealership.domain.car.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

@Embeddable
public class EngineCapacity {
    @Column(nullable = false)
    private double liters;

    protected EngineCapacity() {
    }

    public EngineCapacity(double liters) {
        if (liters <= 0.0) {
            throw new InvalidValueObjectException("Capacity must be positive");
        }

        this.liters = liters;
    }

    public double getLiters() {
        return liters;
    }
}