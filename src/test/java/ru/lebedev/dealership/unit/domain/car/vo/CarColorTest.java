package ru.lebedev.dealership.unit.domain.car.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarColorTest {

    @Test
    void shouldCreateCarColor() {
        CarColor color = new CarColor("Black");

        assertEquals("Black", color.color());
    }

    @Test
    void shouldThrowIfNull() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new CarColor(null)
        );
    }

    @Test
    void shouldThrowIfBlank() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new CarColor(" ")
        );
    }
}