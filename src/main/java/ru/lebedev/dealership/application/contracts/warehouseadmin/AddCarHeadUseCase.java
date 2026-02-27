package ru.lebedev.dealership.application.contracts.warehouseadmin;

import ru.lebedev.dealership.application.contracts.warehouseadmin.operations.AddCarHead;

public interface AddCarHeadUseCase {
    AddCarHead.Response add(AddCarHead.Request request);
}