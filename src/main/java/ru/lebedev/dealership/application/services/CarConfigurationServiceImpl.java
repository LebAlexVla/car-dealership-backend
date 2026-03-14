package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationDefaulterRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationRepository;
import ru.lebedev.dealership.application.contracts.carconfiguration.mappers.CarConfigurationDefaulterInputDtoMapper;
import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;

public class CarConfigurationServiceImpl implements ru.lebedev.dealership.application.contracts.carconfiguration.CarConfigurationService {
    private final CarConfigurationDefaulterRepository carConfigurationDefaulterRepository;
    private final CarConfigurationCustomizerRepository carConfigurationCustomizerRepository;
    private final CarConfigurationRepository carConfigurationRepository;

    public CarConfigurationServiceImpl(CarConfigurationDefaulterRepository carConfigurationDefaulterRepository, CarConfigurationCustomizerRepository carConfigurationCustomizerRepository, CarConfigurationRepository carConfigurationRepository) {
        this.carConfigurationDefaulterRepository = carConfigurationDefaulterRepository;
        this.carConfigurationCustomizerRepository = carConfigurationCustomizerRepository;
        this.carConfigurationRepository = carConfigurationRepository;
    }

    @Override
    public long addDefaulter(AddCarConfigurationDefaulterRequest request) {
        CarConfigurationDefaulter carConfigurationDefaulter = CarConfigurationDefaulterInputDtoMapper.map(request.inputDto());
        carConfigurationDefaulter = carConfigurationDefaulterRepository.save(carConfigurationDefaulter);

        return carConfigurationDefaulter.getCarConfigurationDefaulterId();
    }

    @Override
    public void deleteDefaulter(DeleteCarConfigurationDefaulterRequest request) {
        long carConfigurationDefaulterId = request.carConfigurationDefaulterId();
        carConfigurationDefaulterRepository.delete(carConfigurationDefaulterId);
    }

    @Override
    public long addCustomizer(AddCarConfigurationCustomizerRequest request) {
        long carConfigurationDefaulterId = request.carConfigurationDefaulterId();
        CarConfigurationDefaulter defaulter = carConfigurationDefaulterRepository.findById(carConfigurationDefaulterId);
        CarConfigurationCustomizer customizer = defaulter.create(0, request.clientId());
        customizer = carConfigurationCustomizerRepository.save(customizer);

        return customizer.getCustomCarConfigurationId();
    }

    @Override
    public void deleteCustomizer(DeleteCarConfigurationCustomizerRequest request) {
        long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        carConfigurationCustomizerRepository.delete(carConfigurationCustomizerId);
    }

    @Override
    public void addCustomRequiredDetail(AddCustomDetailRequest request) {
        long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        CarConfigurationCustomizer customizer = carConfigurationCustomizerRepository.findById(carConfigurationCustomizerId);
    }

    @Override
    public void addCustomOptionalDetail(AddCustomDetailRequest request) {

    }

    @Override
    public long createCarConfiguration() {
        return 0;
    }
}