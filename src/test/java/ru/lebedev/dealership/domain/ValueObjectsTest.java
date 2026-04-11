package ru.lebedev.dealership.domain;

import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.car.vo.Price;
import ru.lebedev.dealership.domain.exceptions.InvalidValueObjectException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {

    @Test
    void priceAddShouldReturnSum() {
        Price sum = new Price(BigDecimal.TEN).add(new Price(BigDecimal.ONE));

        assertEquals(new BigDecimal("11"), sum.getRubles());
    }

    @Test
    void priceShouldRejectNegativeValues() {
        assertThrows(InvalidValueObjectException.class, () -> new Price(BigDecimal.valueOf(-1)));
    }

    @Test
    void engineCapacityShouldRejectZeroOrNegative() {
        assertThrows(InvalidValueObjectException.class, () -> new EngineCapacity(0));
        assertThrows(InvalidValueObjectException.class, () -> new EngineCapacity(-1));
    }

    @Test
    void enginePowerShouldRejectZeroOrNegative() {
        assertThrows(InvalidValueObjectException.class, () -> new EnginePower(0L));
        assertThrows(InvalidValueObjectException.class, () -> new EnginePower(-1L));
    }
}
