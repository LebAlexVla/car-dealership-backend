package ru.lebedev.dealership.application.contracts.storageadmin;

import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddCarVersion;

public interface AddCarVersionUseCase {
    AddCarVersion.Response add(AddCarVersion.Request request);
}