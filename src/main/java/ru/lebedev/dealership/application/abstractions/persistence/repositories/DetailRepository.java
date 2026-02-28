package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailId;

public interface DetailRepository {
    Detail save(Detail detail);

    Detail findById(DetailId id);
}
