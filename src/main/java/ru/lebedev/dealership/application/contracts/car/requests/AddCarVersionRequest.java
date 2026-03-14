package ru.lebedev.dealership.application.contracts.car.requests;

import ru.lebedev.dealership.application.contracts.car.models.CarVersionInputDto;

public record AddCarVersionRequest(CarVersionInputDto inputDto) {
}