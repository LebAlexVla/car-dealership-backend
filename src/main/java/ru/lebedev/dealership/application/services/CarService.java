package ru.lebedev.dealership.application.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.application.exceptions.CarHeadNotFoundException;
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

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public Long createHead(CarHead carHead) {
        CarHead savedCarHead = carHeadRepository.save(carHead);
        return savedCarHead.getId();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public Long createVersion(CarVersion carVersion, Long carHeadId) {
        CarHead carHead = carHeadRepository.findById(carHeadId)
                .orElseThrow(() -> new CarHeadNotFoundException(carHeadId));
        carVersion.assignCarHead(carHead);
        CarVersion savedCarVersion = carVersionRepository.save(carVersion);

        return savedCarVersion.getId();
    }

    @Transactional(readOnly = true)
    public Optional<CarHead> findHeadById(Long carHeadId) {
        return carHeadRepository.findById(carHeadId);
    }

    @Transactional(readOnly = true)
    public Optional<CarVersion> findVersionById(Long carVersionId) {
        return carVersionRepository.findById(carVersionId);
    }

    @Transactional(readOnly = true)
    public List<CarHead> findHead(CarHeadFilter filter) {
        return carHeadRepository.findAll(CarHeadSpecifications.fromFilter(filter));
    }

    @Transactional(readOnly = true)
    public List<CarVersion> findVersion(CarVersionFilter filter) {
        return carVersionRepository.findAll(CarVersionSpecifications.fromFilter(filter));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void deleteHead(Long carHeadId) {
        carHeadRepository.deleteById(carHeadId);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void deleteVersion(Long carVersionId) {
        carVersionRepository.deleteById(carVersionId);
    }
}