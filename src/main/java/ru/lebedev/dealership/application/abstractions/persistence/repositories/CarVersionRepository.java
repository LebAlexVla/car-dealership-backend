package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarVersionFilter;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

import java.util.List;

public interface CarVersionRepository {
    CarVersion save(CarVersion carVersion);

    void delete(long carVersionId);

    CarVersion findById(long carVersionId);

    List<CarVersion> findByFilter(CarVersionFilter filter);
}