package ru.lebedev.dealership.domain.detail;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DetailTypeTest {

    @Test
    void shouldCreateDetailType() {
        DetailType type = new DetailType("Smartstream D2.2");

        assertEquals("Smartstream D2.2", type.name());
    }

    @Test
    void shouldThrowIfNull() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new DetailType(null)
        );
    }

    @Test
    void shouldThrowIfBlank() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new DetailType(" ")
        );
    }
}