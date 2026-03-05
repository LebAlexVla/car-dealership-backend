package ru.lebedev.dealership.application.contracts.detail;

import ru.lebedev.dealership.application.contracts.detail.operations.ShowDetails;

public interface ShowDetailsUseCase {
    ShowDetails.Response show(ShowDetails.Request request);
}
