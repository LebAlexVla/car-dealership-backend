package ru.lebedev.dealership.domain.vo;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.exception.InvalidValueObjectException;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

class CarColorTest {

    @Test
    void shouldCreateCarColor() {
        CarColor color = new CarColor("Red");

        assertThat(color.color()).isEqualTo("Red");
    }

    @Test
    void shouldThrowIfNull() {
        assertThatThrownBy(() -> new CarColor(null))
                .isInstanceOf(InvalidValueObjectException.class);
    }

    @Test
    void shouldThrowIfBlank() {
        assertThatThrownBy(() -> new CarColor(" "))
                .isInstanceOf(InvalidValueObjectException.class);
    }
}