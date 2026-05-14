package ru.lebedev.dealership.domain;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.TestDataFactory;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.vo.Price;
import ru.lebedev.dealership.domain.detail.Detail;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarDomainTest {

    @Test
    void carVersionShouldAddColorsOnlyWhenNotNull() {
        CarVersion carVersion = TestDataFactory.carVersion(1L);
        int sizeBefore = carVersion.getColors().size();

        carVersion.addColors(List.of("white", "red"));
        carVersion.addColors(null);

        assertEquals(sizeBefore + 2, carVersion.getColors().size());
    }

    @Test
    void carVersionShouldReturnCopyOfColors() {
        CarVersion carVersion = TestDataFactory.carVersion(1L);

        assertThrows(UnsupportedOperationException.class, () -> carVersion.getColors().add("blue"));
    }

    @Test
    void carVersionShouldAssignHeadAndUpdatePriceAndAvailability() {
        CarVersion carVersion = TestDataFactory.carVersion(1L);
        CarHead newHead = TestDataFactory.carHead(99L);

        carVersion.assignCarHead(newHead);
        carVersion.setPrice(new Price(BigDecimal.valueOf(99)));
        carVersion.setTestDriveAvailable(true);

        assertEquals(newHead, carVersion.getCarHead());
        assertEquals(new BigDecimal("99"), carVersion.getPrice().getRubles());
        assertTrue(carVersion.isTestDriveAvailable());
    }

    @Test
    void detailShouldManageCompatibility() {
        CarVersion carVersion = TestDataFactory.carVersion(1L);
        Detail detail = TestDataFactory.detail(1L, "Bose", "audio", null);

        assertFalse(detail.checkCompatibility(carVersion));

        detail.addCompatibleCar(carVersion);
        assertTrue(detail.checkCompatibility(carVersion));

        detail.removeCompatibleCar(carVersion);
        assertFalse(detail.checkCompatibility(carVersion));
    }

    @Test
    void carConfigurationShouldCountPriceAndReturnDetailsCopy() {
        CarVersion carVersion = TestDataFactory.carVersion(1L);
        Detail detail = TestDataFactory.detail(2L, "Matrix", "light", carVersion);

        var config = new ru.lebedev.dealership.domain.carconfiguration.CarConfiguration(
                TestDataFactory.user(1L),
                carVersion,
                new HashSet<>(List.of(detail))
        );

        assertEquals(new BigDecimal("2010000"), config.countPrice().getRubles());

        var details = config.getDetails();
        details.clear();

        assertEquals(1, config.getDetails().size());
    }
}