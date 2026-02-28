package ru.lebedev.dealership.application.contracts.car.operations;

import ru.lebedev.dealership.application.contracts.car.filters.CarHeadFilter;
import ru.lebedev.dealership.domain.car.entities.CarHead;

import java.util.List;
import java.util.UUID;

public final class ShowCarsHeads {
        private ShowCarsHeads() {}

        public record Request(UUID userId, CarHeadFilter filter) {}

        public sealed interface Response permits Success, Failure {}

        public record Success(List<CarHead> carHeads) implements Response {}

        public record Failure(String message) implements Response {}
}
