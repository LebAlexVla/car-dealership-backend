package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;

import java.util.List;

public interface CarsForTestDriveRepository {
    List<CarVersionId> add(List<CarVersionId> carsIds);

    List<CarVersionId> findAll();
}
