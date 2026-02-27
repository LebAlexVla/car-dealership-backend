package ru.lebedev.dealership.domain.car.entities;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.car.valueobjects.Engine;

public record CarVersion(
        CarVersionId id,
        String name,
        CarHeadId headId,
        Engine engine,
        GearboxType gearboxType,
        CarDrive carDrive
) {}