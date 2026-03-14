package ru.lebedev.dealership.domain.car.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnginePowerTest {

    @Test
    void shouldCreatePower() {
        EnginePower power = new EnginePower(249L);

        assertEquals(249L, power.horsepower());
    }

    @Test
    void shouldThrowIfZero() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new EnginePower(0L)
        );
    }

    @Test
    void shouldThrowIfNegative() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new EnginePower(-10L)
        );
    }
}