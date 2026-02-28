package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.operations.AddCarHead;

public interface AddCarHeadUseCase {
    AddCarHead.Response add(AddCarHead.Request request);
}