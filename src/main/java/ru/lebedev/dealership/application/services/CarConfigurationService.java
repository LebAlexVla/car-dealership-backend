package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationDefaulterRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;

@Service
public class CarConfigurationService {
    private final CarConfigurationDefaulterRepository defaulterRepository;
    private final CarConfigurationCustomizerRepository customizerRepository;
    private final CarConfigurationRepository carConfigurationRepository;

    public CarConfigurationService(CarConfigurationDefaulterRepository carConfigurationDefaulterRepository, CarConfigurationCustomizerRepository carConfigurationCustomizerRepository, CarConfigurationRepository carConfigurationRepository) {
        this.defaulterRepository = carConfigurationDefaulterRepository;
        this.customizerRepository = carConfigurationCustomizerRepository;
        this.carConfigurationRepository = carConfigurationRepository;
    }

    public Long createDefaulter(CarConfigurationDefaulter defaulter) {
        CarConfigurationDefaulter savedDefaulter = defaulterRepository.save(defaulter);
        return savedDefaulter.getId();
    }

    public void deleteDefaulter(Long defaulterId) {
        defaulterRepository.deleteById(defaulterId);
    }
}