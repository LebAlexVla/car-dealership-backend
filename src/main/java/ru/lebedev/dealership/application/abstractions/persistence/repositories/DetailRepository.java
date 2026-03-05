package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.application.abstractions.persistence.queries.DetailFilter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailId;

import java.util.List;

public interface DetailRepository {
    Detail save(Detail detail);

    Detail findById(DetailId id);

    List<Detail> findByFilter(DetailFilter filter);
}