package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.filters.CarHeadFilter;
import ru.lebedev.dealership.application.filters.CarVersionFilter;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarHeadRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.specification.CarHeadSpecifications;
import ru.lebedev.dealership.infrastructure.persistence.specification.CarVersionSpecifications;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarHeadRepository carHeadRepository;
    private final CarVersionRepository carVersionRepository;

    public CarService(CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository) {
        this.carHeadRepository = carHeadRepository;
        this.carVersionRepository = carVersionRepository;
    }

    public Long createHead(CarHead carHead) {
        CarHead savedCarHead = carHeadRepository.save(carHead);
        return savedCarHead.getId();
    }

    public Long createVersion(CarVersion carVersion) {
        CarVersion savedCarVersion = carVersionRepository.save(carVersion);
        return savedCarVersion.getId();
    }

    public Optional<CarHead> findHeadById(Long carHeadId) {
        return carHeadRepository.findById(carHeadId);
    }

    public Optional<CarVersion> findVersionById(Long carVersionId) {
        return carVersionRepository.findById(carVersionId);
    }

    public List<CarHead> findHead(CarHeadFilter filter) {
        return carHeadRepository.findAll(CarHeadSpecifications.fromFilter(filter));
    }

    public List<CarVersion> findVersion(CarVersionFilter filter) {
        return carVersionRepository.findAll(CarVersionSpecifications.fromFilter(filter));
    }

    public void deleteHead(Long carHeadId) {
        carHeadRepository.deleteById(carHeadId);
    }

    public void deleteVersion(Long carVersionId) {
        carVersionRepository.deleteById(carVersionId);
    }
}