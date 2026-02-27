package ru.lebedev.dealership.application.contracts.storageadmin;

import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddCarHead;

public interface AddCarHeadUseCase {
    AddCarHead.Response add(AddCarHead.Request request);
}