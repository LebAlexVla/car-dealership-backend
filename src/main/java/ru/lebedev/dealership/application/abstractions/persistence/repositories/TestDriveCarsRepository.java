package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import java.util.List;

public interface TestDriveCarsRepository {
    void add(List<Long> carsIds);

    void delete(List<Long> carsIds);

    List<Long> findAll();
}