package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.infrastructure.persistence.repository.DetailRepository;
import ru.lebedev.dealership.infrastructure.persistence.specification.DetailSpecifications;

import java.util.List;
import java.util.Optional;

@Service
public class DetailService {
    private final DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public Long create(Detail detail) {
        Detail savedDetail = detailRepository.save(detail);
        return savedDetail.getId();
    }

    public Optional<Detail> getById(Long id) {
        return detailRepository.findById(id);
    }

    public List<Detail> find(DetailFilter filter) {
        return detailRepository.findAll(DetailSpecifications.fromFilter(filter));
    }

    public void delete(Long id) {
        detailRepository.deleteById(id);
    }
}