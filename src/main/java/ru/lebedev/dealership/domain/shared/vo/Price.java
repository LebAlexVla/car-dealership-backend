package ru.lebedev.dealership.domain.shared.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import java.math.BigDecimal;

@Embeddable
public class Price {
    @Column(nullable = false)
    private BigDecimal rubles;

    protected Price() {}

    public Price(BigDecimal rubles) {
        if (rubles.compareTo(BigDecimal.ZERO) < 0L) {
            throw new InvalidValueObjectException("Price can't be negative");
        }

        this.rubles = rubles;
    }

    public Price add(Price other) {
        return new Price(this.rubles.add(other.rubles));
    }

    public BigDecimal getRubles() {
        return rubles;
    }
}