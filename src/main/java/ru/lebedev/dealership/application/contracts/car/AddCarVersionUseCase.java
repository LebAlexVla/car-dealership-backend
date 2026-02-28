package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.operations.AddCarVersion;

public interface AddCarVersionUseCase {
    AddCarVersion.Response add(AddCarVersion.Request request);
}