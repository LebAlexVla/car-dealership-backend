package ru.lebedev.dealership.domain.shared.vo;

import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import java.math.BigDecimal;

public record Price(BigDecimal rubles) {
    public Price {
        if (rubles.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidValueObjectException("Price can't be negative");
        }
    }

    public Price add(Price other) {
        return new Price(this.rubles.add(other.rubles));
    }
}
