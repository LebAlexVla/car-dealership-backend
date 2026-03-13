package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarHeadFilter;
import ru.lebedev.dealership.domain.car.entities.CarHead;

import java.util.List;

public interface CarHeadRepository {
    CarHead save(CarHead carHead);

    void delete(long carHeadId);

    CarHead findById(long carHeadId);

    List<CarHead> findByFilter(CarHeadFilter filter);
}