package ru.lebedev.dealership.unit.domain.car.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EngineCapacityTest {

    @Test
    void shouldCreateCapacity() {
        EngineCapacity capacity = new EngineCapacity(2.5);

        assertEquals(2.5, capacity.liters());
    }

    @Test
    void shouldThrowIfZero() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new EngineCapacity(0L)
        );
    }

    @Test
    void shouldThrowIfNegative() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new EngineCapacity(-1L)
        );
    }
}