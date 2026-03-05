package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadFilterDto;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadOutputDto;

import java.util.List;

public final class ShowCarsHeads {
    private ShowCarsHeads() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, CarHeadFilterDto filterDto) {
    }

    public record Success(List<CarHeadOutputDto> carHeads) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}