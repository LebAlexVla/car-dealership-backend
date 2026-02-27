package ru.lebedev.dealership.application.contracts.storageadmin.operations;

import ru.lebedev.dealership.domain.car.enums.BodyType;
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

    public record Success(long carHeadId) implements Response {}

    public record Failure(String message) implements Response {}
}
