package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationDefaulterRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationRepository;
import ru.lebedev.dealership.application.contracts.carconfiguration.CarConfigurationUseCase;
import ru.lebedev.dealership.application.contracts.carconfiguration.mappers.CarConfigurationDefaulterInputDtoMapper;
import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;

public class CarConfigurationService implements CarConfigurationUseCase {
    private final CarConfigurationDefaulterRepository carConfigurationDefaulterRepository;
    private final CarConfigurationCustomizerRepository carConfigurationCustomizerRepository;
    private final CarConfigurationRepository carConfigurationRepository;

    public CarConfigurationService(CarConfigurationDefaulterRepository carConfigurationDefaulterRepository, CarConfigurationCustomizerRepository carConfigurationCustomizerRepository, CarConfigurationRepository carConfigurationRepository) {
        this.carConfigurationDefaulterRepository = carConfigurationDefaulterRepository;
        this.carConfigurationCustomizerRepository = carConfigurationCustomizerRepository;
        this.carConfigurationRepository = carConfigurationRepository;
    }

    @Override
    public long AddDefaulter(AddCarConfigurationDefaulterRequest request) {
        CarConfigurationDefaulter carConfigurationDefaulter = CarConfigurationDefaulterInputDtoMapper.map(request.inputDto());
        carConfigurationDefaulter = carConfigurationDefaulterRepository.save(carConfigurationDefaulter);

        return carConfigurationDefaulter.getCarConfigurationDefaulterId();
    }

    @Override
    public void DeleteDefaulter(DeleteCarConfigurationDefaulterRequest request) {
        long carConfigurationDefaulterId = request.carConfigurationDefaulterId();
        carConfigurationDefaulterRepository.delete(carConfigurationDefaulterId);
    }

    @Override
    public long AddCustomizer(AddCarConfigurationCustomizerRequest request) {
        long carConfigurationDefaulterId = request.carConfigurationDefaulterId();
        CarConfigurationDefaulter defaulter = carConfigurationDefaulterRepository.findById(carConfigurationDefaulterId);
        CarConfigurationCustomizer customizer = defaulter.create(0, request.clientId());
        customizer = carConfigurationCustomizerRepository.save(customizer);

        return customizer.getCustomCarConfigurationId();
    }

    @Override
    public void DeleteCustomizer(DeleteCarConfigurationCustomizerRequest request) {
        long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        carConfigurationCustomizerRepository.delete(carConfigurationCustomizerId);
    }

    @Override
    public void AddCustomRequiredDetail(AddCustomDetailRequest request) {
        long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        CarConfigurationCustomizer customizer = carConfigurationCustomizerRepository.findById(carConfigurationCustomizerId);
    }

    @Override
    public void AddCustomOptionalDetail(AddCustomDetailRequest request) {

    }

    @Override
    public long CreateCarConfiguration() {
        return 0;
    }
}