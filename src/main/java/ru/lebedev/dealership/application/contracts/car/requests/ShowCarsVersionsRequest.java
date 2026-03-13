package ru.lebedev.dealership.application.contracts.car.requests;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionFilterDto;

public record ShowCarsVersionsRequest(long userId, CarVersionFilterDto filterDto) {
}
