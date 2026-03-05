package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarVersionFilter;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;

import java.util.List;

public interface CarVersionRepository {
    CarVersion save(CarVersion carVersion);

    CarVersion findById(CarVersionId carVersionId);

    List<CarVersion> findByFilter(CarVersionFilter filter);
}