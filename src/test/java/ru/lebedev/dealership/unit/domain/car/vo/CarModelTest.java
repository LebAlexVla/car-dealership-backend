package ru.lebedev.dealership.unit.domain.car.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarModelTest {

    @Test
    void shouldCreateModel() {
        CarModel model = new CarModel("Santa Fe");

        assertEquals("Santa Fe", model.name());
    }

    @Test
    void shouldThrowIfNull() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new CarModel(null)
        );
    }

    @Test
    void shouldThrowIfBlank() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new CarModel(" ")
        );
    }
}