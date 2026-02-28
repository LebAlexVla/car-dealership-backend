package ru.lebedev.dealership.application.contracts.detail;

import ru.lebedev.dealership.application.contracts.detail.operations.AddDetail;

public interface AddDetailUseCase {
    AddDetail.Response add(AddDetail.Request request);
}
