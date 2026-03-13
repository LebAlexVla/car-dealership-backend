package ru.lebedev.dealership.application.contracts.car.requests;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadInputDto;

public record AddCarHeadRequest(long userId, CarHeadInputDto inputDto) {
}