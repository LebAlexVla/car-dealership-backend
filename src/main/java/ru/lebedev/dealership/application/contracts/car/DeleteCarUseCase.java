package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.requests.DeleteCarHeadRequest;
import ru.lebedev.dealership.application.contracts.car.requests.DeleteCarVersionRequest;

public interface DeleteCarUseCase {
    void DeleteCarHead(DeleteCarHeadRequest request);

    void DeleteCarVersion(DeleteCarVersionRequest request);
}
