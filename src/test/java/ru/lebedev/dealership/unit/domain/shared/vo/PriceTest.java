package ru.lebedev.dealership.unit.domain.shared.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void shouldCreatePrice() {
        Price price = new Price(BigDecimal.valueOf(3000000L));

        assertEquals(BigDecimal.valueOf(3000000L), price.rubles());
    }

    @Test
    void shouldThrowIfNegative() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new Price(BigDecimal.valueOf(-1L))
        );
    }

    @Test
    void shouldAddPrices() {
        Price p1 = new Price(BigDecimal.valueOf(100L));
        Price p2 = new Price(BigDecimal.valueOf(200L));

        Price result = p1.add(p2);

        assertEquals(BigDecimal.valueOf(300L), result.rubles());
    }

    @Test
    void addShouldNotMutateOriginal() {

        Price p1 = new Price(new BigDecimal("100"));
        Price p2 = new Price(new BigDecimal("50"));

        Price result = p1.add(p2);

        assertEquals(new BigDecimal("100"), p1.rubles());
        assertEquals(new BigDecimal("150"), result.rubles());
    }
}