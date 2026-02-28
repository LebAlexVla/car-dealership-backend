package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.filters.CarVersionFilter;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

import java.util.List;
import java.util.UUID;

public final class ShowCarsVersions {
    private ShowCarsVersions() {}

    public record Request(UUID userId, CarVersionFilter filter) {}

    public sealed interface Response permits Success, Failure {}

    public record Success(List<CarVersion> carVersions) implements Response {}

    public record Failure(String message) implements Response {}
}

