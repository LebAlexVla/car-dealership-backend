package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;

import java.util.List;

public interface CarHeadRepository {
    CarHead save(CarHead carHead);

    CarHead findById(CarHeadId carHeadId);

    List<CarHead> findAll();
}
