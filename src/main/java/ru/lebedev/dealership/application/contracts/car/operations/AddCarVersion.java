package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionInputDto;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;

public final class AddCarVersion {
    private AddCarVersion() {}

    public record Request(CarVersionInputDto inputDto) {}

    public sealed interface Response permits Success, Failure {}

    public record Success(String carVersionId) implements Response {}

    public record Failure(String message) implements Response {}
}