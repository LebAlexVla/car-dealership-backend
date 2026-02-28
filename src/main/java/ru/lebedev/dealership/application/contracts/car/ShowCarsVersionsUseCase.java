package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.operations.ShowCarsVersions;

public interface ShowCarsVersionsUseCase {
    ShowCarsVersions.Response show(ShowCarsVersions.Request request);
}
