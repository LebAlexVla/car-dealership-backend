package ru.lebedev.dealership.application.abstractions.persistence.repositories;

import ru.lebedev.dealership.application.abstractions.persistence.queries.DetailFilter;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.List;

public interface DetailRepository {
    Detail save(Detail detail);

    void delete(long detailId);

    Detail findById(long detailId);

    List<Detail> findByFilter(DetailFilter filter);
}