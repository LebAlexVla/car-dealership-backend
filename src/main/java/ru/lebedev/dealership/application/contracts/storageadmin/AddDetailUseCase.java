package ru.lebedev.dealership.application.contracts.storageadmin;

import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddDetail;

public interface AddDetailUseCase {
    AddDetail.Response add(AddDetail.Request request);
}
