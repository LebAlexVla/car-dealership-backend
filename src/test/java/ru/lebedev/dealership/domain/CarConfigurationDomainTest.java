package ru.lebedev.dealership.domain;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.TestDataFactory;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.exceptions.DuplicateDefaultDetailTypeException;
import ru.lebedev.dealership.domain.exceptions.IncompatibleDetailException;
import ru.lebedev.dealership.domain.exceptions.NoSuchOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongRequiredDetailException;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarConfigurationDomainTest {

    @Test
    void defaulterShouldRejectDuplicateRequiredDetailType() {
        var car = TestDataFactory.carVersion(1L);
        var defaultAudio = TestDataFactory.detail(1L, "Basic Audio", "audio", car);
        var defaulter = new CarConfigurationDefaulter(car, new HashSet<>(List.of(defaultAudio)));

        var duplicateType = TestDataFactory.detail(2L, "Premium Audio", "audio", car);

        assertThrows(DuplicateDefaultDetailTypeException.class, () -> defaulter.addRequiredDetail(duplicateType));
    }

    @Test
    void defaulterShouldCreateCustomizerWithCopyOfDetails() {
        var car = TestDataFactory.carVersion(1L);
        var detail = TestDataFactory.detail(1L, "Basic Audio", "audio", car);
        var defaulter = new CarConfigurationDefaulter(car, new HashSet<>(List.of(detail)));

        CarConfigurationCustomizer customizer = defaulter.create(TestDataFactory.user(1L));

        assertEquals(1, customizer.getRequiredDetails().size());

        defaulter.removeRequiredDetail("audio");

        assertEquals(1, customizer.getRequiredDetails().size());
    }

    @Test
    void customizerShouldHandleRequiredAndOptionalSelectionAndBuild() {
        var car = TestDataFactory.carVersion(1L);
        var required = TestDataFactory.detail(1L, "Basic Audio", "audio", car);
        var requiredReplacement = TestDataFactory.detail(2L, "Premium Audio", "audio", car);
        var optional = TestDataFactory.detail(3L, "Sunroof", "roof", car);

        var customizer = new CarConfigurationCustomizer(
                TestDataFactory.user(1L),
                car,
                new HashSet<>(List.of(required))
        );

        customizer.selectRequiredDetail(requiredReplacement);
        customizer.selectOptionalDetail(optional);

        assertEquals(1, customizer.getRequiredDetails().size());
        assertEquals(1, customizer.getOptionalDetails().size());
        assertEquals(2, customizer.build().getDetails().size());

        customizer.rejectOptionalDetail(optional);

        assertEquals(0, customizer.getOptionalDetails().size());
    }

    @Test
    void customizerShouldThrowForIncompatibleOrWrongDetails() {
        var car = TestDataFactory.carVersion(1L);
        var otherCar = TestDataFactory.carVersion(2L);
        var required = TestDataFactory.detail(1L, "Basic Audio", "audio", car);
        var incompatible = TestDataFactory.detail(2L, "Offroad", "suspension", otherCar);
        var requiredAgain = TestDataFactory.detail(3L, "Other Audio", "audio", car);

        var customizer = new CarConfigurationCustomizer(
                TestDataFactory.user(1L),
                car,
                new HashSet<>(List.of(required))
        );

        assertThrows(IncompatibleDetailException.class, () -> customizer.selectRequiredDetail(incompatible));
        assertThrows(IncompatibleDetailException.class, () -> customizer.selectOptionalDetail(incompatible));
        assertThrows(
                WrongRequiredDetailException.class,
                () -> customizer.selectRequiredDetail(TestDataFactory.detail(4L, "Matrix", "lights", car))
        );
        assertThrows(WrongOptionalDetailException.class, () -> customizer.selectOptionalDetail(requiredAgain));
        assertThrows(NoSuchOptionalDetailException.class, () -> customizer.rejectOptionalDetail(requiredAgain));
    }
}