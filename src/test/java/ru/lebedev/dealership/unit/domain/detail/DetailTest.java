package ru.lebedev.dealership.unit.domain.detail;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DetailTest {

    @Test
    void shouldCheckCompatibility() {

        Set<Long> cars = new HashSet<>();
        cars.add(1L);

        Detail detail = new Detail(
                1L,
                "V8",
                new DetailType("Engine"),
                null,
                cars
        );

        assertTrue(detail.checkCompatibility(1L));
        assertFalse(detail.checkCompatibility(2L));
    }

    @Test
    void shouldAddCompatibleCar() {

        Detail detail = new Detail(
                10L,
                "V8",
                new DetailType("Engine"),
                null,
                new HashSet<>()
        );

        detail.addCompatibleCar(5L);

        assertTrue(detail.compatibleCars().contains(5L));
    }

    @Test
    void shouldRemoveCompatibleCar() {

        Set<Long> cars = new HashSet<>();
        cars.add(5L);

        Detail detail = new Detail(
                10L,
                "V8",
                new DetailType("Engine"),
                null,
                cars
        );

        detail.removeCompatibleCar(5L);

        assertFalse(detail.compatibleCars().contains(5L));
    }
}