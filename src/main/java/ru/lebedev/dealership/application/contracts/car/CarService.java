package ru.lebedev.dealership.application.contracts.car;

import ru.lebedev.dealership.application.contracts.car.models.CarHeadOutputDto;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionOutputDto;
import ru.lebedev.dealership.application.contracts.car.requests.*;

import java.util.List;

public interface CarService {
    Long AddCarHead(AddCarHeadRequest request);

    Long AddCarVersion(AddCarVersionRequest request);

    void DeleteCarHead(DeleteCarHeadRequest request);

    void DeleteCarVersion(DeleteCarVersionRequest request);

    CarHeadOutputDto ShowSpecificCarHead(ShowSpecificCarHeadRequest request);

    CarVersionOutputDto ShowSpecificCarVersion(ShowSpecificCarVersionRequest request);

    List<CarHeadOutputDto> ShowCarsHeads(ShowCarsHeadsRequest request);

    List<CarVersionOutputDto> ShowCarsVersions(ShowCarsVersionsRequest request);
}