package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.DetailRepository;
import ru.lebedev.dealership.infrastructure.persistence.specification.DetailSpecifications;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DetailService {
    private final DetailRepository detailRepository;
    private final CarVersionRepository carVersionRepository;

    public DetailService(DetailRepository detailRepository, CarVersionRepository carVersionRepository) {
        this.detailRepository = detailRepository;
        this.carVersionRepository = carVersionRepository;
    }

    public Long create(Detail detail, Set<Long> carIds) {
        if (carIds != null) {
            carVersionRepository.findAllById(carIds).forEach(detail::addCompatibleCar);
        }
        Detail savedDetail = detailRepository.save(detail);

        return savedDetail.getId();
    }

    public Optional<Detail> findById(Long id) {
        return detailRepository.findById(id);
    }

    public List<Detail> find(DetailFilter filter) {
        return detailRepository.findAll(DetailSpecifications.fromFilter(filter));
    }

    public void delete(Long id) {
        detailRepository.deleteById(id);
    }
}