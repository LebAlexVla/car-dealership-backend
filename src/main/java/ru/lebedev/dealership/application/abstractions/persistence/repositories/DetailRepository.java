package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.car.entities.Detail;
import ru.lebedev.dealership.domain.car.valueobjects.DetailId;

public interface DetailRepository {
    Detail save(Detail detail);

    Detail findById(DetailId id);
}
