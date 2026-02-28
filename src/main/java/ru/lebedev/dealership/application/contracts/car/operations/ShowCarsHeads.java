package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarHeadFilter;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadFilterDto;
import ru.lebedev.dealership.application.contracts.car.models.CarsHeadsOutputDto;
import ru.lebedev.dealership.domain.car.entities.CarHead;

import java.util.List;

public final class ShowCarsHeads {
        private ShowCarsHeads() {}

        public record Request(String userId, CarHeadFilterDto filterDto) {}

        public sealed interface Response permits Success, Failure {}

        public record Success(CarsHeadsOutputDto outputDto) implements Response {}

        public record Failure(String message) implements Response {}
}
