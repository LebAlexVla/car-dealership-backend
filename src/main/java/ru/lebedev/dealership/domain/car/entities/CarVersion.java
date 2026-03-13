package ru.lebedev.dealership.domain.car.entities;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.CarColor;
import ru.lebedev.dealership.domain.car.vo.Engine;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.List;

public record CarVersion(
        long carVersionId,
        String name,
        long carHeadId,
        Engine engine,
        GearboxType gearboxType,
        CarDrive carDrive,
        List<CarColor> colors,
        Price price
) {
}