package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarModel;
import ru.lebedev.dealership.domain.shared.valueobjects.Brand;

import java.util.UUID;

public final class AddCarHead {
    private AddCarHead() {}

    public record Request(
            UUID userId,
            Brand brand,
            CarModel model,
            BodyType bodyType
    ) {}

    public sealed interface Response permits Success, Failure {}

    public record Success(CarHeadId carHeadId) implements Response {}

    public record Failure(String message) implements Response {}
}
