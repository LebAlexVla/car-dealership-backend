package ru.lebedev.dealership.application.contracts.car.requests;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadFilterDto;

public record ShowCarsHeadsRequest(CarHeadFilterDto filterDto) {
}