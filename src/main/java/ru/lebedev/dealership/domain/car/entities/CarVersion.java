package ru.lebedev.dealership.domain.car.entities;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.valueobjects.CarColor;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.car.valueobjects.Engine;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

import java.util.List;

public record CarVersion(
        CarVersionId id,
        String name,
        CarHeadId headId,
        Engine engine,
        GearboxType gearboxType,
        CarDrive carDrive,
        List<CarColor> colors,
        Price price
) {}