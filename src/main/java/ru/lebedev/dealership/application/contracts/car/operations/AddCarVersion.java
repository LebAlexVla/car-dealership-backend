package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.valueobjects.CarColor;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.car.valueobjects.Engine;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

import java.util.UUID;

public final class AddCarVersion {
    private AddCarVersion() {}

    public record Request(
            UUID userId,
            CarHeadId carHeadId,
            String carVersionName,
            Engine engine,
            GearboxType gearBoxType,
            CarDrive carDrive,
            CarColor[] colors,
            Price price
    ) {}

    public sealed interface Response permits Success, Failure {}

    public record Success(CarVersionId carVersionId) implements Response {}

    public record Failure(String message) implements Response {}
}