package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadOutputDto;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionOutputDto;
import ru.lebedev.dealership.application.contracts.car.requests.*;

import java.util.List;

public interface CarService {
    Long addCarHead(AddCarHeadRequest request);

    Long addCarVersion(AddCarVersionRequest request);

    void deleteCarHead(DeleteCarHeadRequest request);

    void deleteCarVersion(DeleteCarVersionRequest request);

    CarHeadOutputDto showSpecificCarHead(ShowSpecificCarHeadRequest request);

    CarVersionOutputDto showSpecificCarVersion(ShowSpecificCarVersionRequest request);

    List<CarHeadOutputDto> showCarsHeads(ShowCarsHeadsRequest request);

    List<CarVersionOutputDto> showCarsVersions(ShowCarsVersionsRequest request);
}