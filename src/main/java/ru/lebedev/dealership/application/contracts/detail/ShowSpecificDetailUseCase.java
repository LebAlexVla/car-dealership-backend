package ru.lebedev.dealership.application.contracts.detail;

import ru.lebedev.dealership.application.contracts.detail.operations.ShowSpecificDetail;

public interface ShowSpecificDetailUseCase {
    ShowSpecificDetail.Response show(ShowSpecificDetail.Request request);
}