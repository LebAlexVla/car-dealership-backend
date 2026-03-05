package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionFilterDto;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionOutputDto;

import java.util.List;

public final class ShowCarsVersions {
    private ShowCarsVersions() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, CarVersionFilterDto filterDto) {
    }

    public record Success(List<CarVersionOutputDto> carVersions) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}

