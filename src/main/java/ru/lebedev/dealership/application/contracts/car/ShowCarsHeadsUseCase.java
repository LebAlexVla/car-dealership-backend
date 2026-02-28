package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.operations.ShowCarsHeads;

public interface ShowCarsHeadsUseCase {
    ShowCarsHeads.Response show(ShowCarsHeads.Request request);
}
