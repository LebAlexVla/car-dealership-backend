package ru.lebedev.dealership.application.contracts.warehouseadmin;

import ru.lebedev.dealership.application.contracts.warehouseadmin.operations.AddCarVersion;

public interface AddCarVersionUseCase {
    AddCarVersion.Response add(AddCarVersion.Request request);
}