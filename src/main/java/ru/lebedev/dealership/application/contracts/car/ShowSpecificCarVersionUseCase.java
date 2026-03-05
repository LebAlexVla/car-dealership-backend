package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.operations.ShowSpecificCarVersion;

public interface ShowSpecificCarVersionUseCase {
    ShowSpecificCarVersion.Response show(ShowSpecificCarVersion.Request request);
}
