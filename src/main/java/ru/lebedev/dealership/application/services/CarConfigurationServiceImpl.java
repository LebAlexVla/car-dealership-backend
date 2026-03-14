package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationDefaulterRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarConfigurationRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.contracts.carconfiguration.CarConfigurationService;
import ru.lebedev.dealership.application.contracts.carconfiguration.mappers.CarConfigurationDefaulterInputDtoMapper;
import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;

public class CarConfigurationServiceImpl implements CarConfigurationService {
    private final CarConfigurationDefaulterRepository carConfigurationDefaulterRepository;
    private final CarConfigurationCustomizerRepository carConfigurationCustomizerRepository;
    private final CarConfigurationRepository carConfigurationRepository;
    private final DetailRepository detailRepository;

    public CarConfigurationServiceImpl(CarConfigurationDefaulterRepository carConfigurationDefaulterRepository, CarConfigurationCustomizerRepository carConfigurationCustomizerRepository, CarConfigurationRepository carConfigurationRepository, DetailRepository detailRepository) {
        this.carConfigurationDefaulterRepository = carConfigurationDefaulterRepository;
        this.carConfigurationCustomizerRepository = carConfigurationCustomizerRepository;
        this.carConfigurationRepository = carConfigurationRepository;
        this.detailRepository = detailRepository;
    }

    @Override
    public Long addDefaulter(AddCarConfigurationDefaulterRequest request) {
        CarConfigurationDefaulter carConfigurationDefaulter = CarConfigurationDefaulterInputDtoMapper.map(request.inputDto());
        carConfigurationDefaulter = carConfigurationDefaulterRepository.save(carConfigurationDefaulter);

        return carConfigurationDefaulter.getCarConfigurationDefaulterId();
    }

    @Override
    public void deleteDefaulter(DeleteCarConfigurationDefaulterRequest request) {
        Long carConfigurationDefaulterId = request.carConfigurationDefaulterId();
        carConfigurationDefaulterRepository.delete(carConfigurationDefaulterId);
    }

    @Override
    public Long addCustomizer(AddCarConfigurationCustomizerRequest request) {
        Long carConfigurationDefaulterId = request.carConfigurationDefaulterId();
        CarConfigurationDefaulter defaulter = carConfigurationDefaulterRepository.findById(carConfigurationDefaulterId);
        CarConfigurationCustomizer customizer = defaulter.create(0L, request.clientId());
        customizer = carConfigurationCustomizerRepository.save(customizer);

        return customizer.getCustomCarConfigurationId();
    }

    @Override
    public void deleteCustomizer(DeleteCarConfigurationCustomizerRequest request) {
        Long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        carConfigurationCustomizerRepository.delete(carConfigurationCustomizerId);
    }

    @Override
    public void addCustomRequiredDetail(AddCustomDetailRequest request) {
        Long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        Long detailId = request.detailId();

        CarConfigurationCustomizer customizer = carConfigurationCustomizerRepository.findById(carConfigurationCustomizerId);
        Detail detail = detailRepository.findById(detailId);

        customizer.withRequiredDetail(detail);
    }

    @Override
    public void addCustomOptionalDetail(AddCustomDetailRequest request) {
        Long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        Long detailId = request.detailId();

        CarConfigurationCustomizer customizer = carConfigurationCustomizerRepository.findById(carConfigurationCustomizerId);
        Detail detail = detailRepository.findById(detailId);

        customizer.withOptionalDetail(detail);
    }

    @Override
    public Long createCarConfiguration(CreateCarConfigurationRequest request) {
        Long carConfigurationCustomizerId = request.carConfigurationCustomizerId();
        CarConfigurationCustomizer customizer = carConfigurationCustomizerRepository.findById(carConfigurationCustomizerId);
        CarConfiguration carConfiguration = customizer.build(0L);
        carConfiguration = carConfigurationRepository.save(carConfiguration);

        return carConfiguration.configurationId();
    }
}