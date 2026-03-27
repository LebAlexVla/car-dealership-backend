package ru.lebedev.dealership.unit.domain.shared.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    void shouldCreateBrand() {
        Brand brand = new Brand("Hyundai");

        assertEquals("Hyundai", brand.name());
    }

    @Test
    void shouldThrowIfNull() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new Brand(null)
        );
    }

    @Test
    void shouldThrowIfBlank() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new Brand(" ")
        );
    }
}