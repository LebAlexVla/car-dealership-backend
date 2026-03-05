package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.operations.ShowSpecificCarHead;

public interface ShowSpecificCarHeadUseCase {
    ShowSpecificCarHead.Response show(ShowSpecificCarHead.Request request);
}
