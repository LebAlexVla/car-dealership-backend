package ru.lebedev.dealership.domain.car.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

@Embeddable
public class EnginePower {
    @Column(nullable = false)
    private Long horsepower;

    protected EnginePower() {
    }

    public EnginePower(Long horsepower) {
        if (horsepower <= 0L) {
            throw new InvalidValueObjectException("Power must be positive");
        }

        this.horsepower = horsepower;
    }

    public Long getHorsepower() {
        return horsepower;
    }
}