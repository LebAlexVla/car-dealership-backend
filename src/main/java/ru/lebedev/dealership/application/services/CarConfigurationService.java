package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.exceptions.*;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CarConfigurationService {
    private final CarConfigurationDefaulterRepository defaulterRepository;
    private final CarConfigurationCustomizerRepository customizerRepository;
    private final CarConfigurationRepository configurationRepository;
    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;
    private final DetailRepository detailRepository;

    public CarConfigurationService(CarConfigurationDefaulterRepository carConfigurationDefaulterRepository, CarConfigurationCustomizerRepository carConfigurationCustomizerRepository, CarConfigurationRepository carConfigurationRepository, UserRepository userRepository, CarVersionRepository carVersionRepository, DetailRepository detailRepository) {
        this.defaulterRepository = carConfigurationDefaulterRepository;
        this.customizerRepository = carConfigurationCustomizerRepository;
        this.configurationRepository = carConfigurationRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
        this.detailRepository = detailRepository;
    }

    public Long createDefaulter(Long carVersionId, Set<Long> requiredDetailsIds) {
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        Set<Detail> requiredDetails = new HashSet<>(detailRepository.findAllById(requiredDetailsIds));
        CarConfigurationDefaulter savedDefaulter = defaulterRepository.save(
                new CarConfigurationDefaulter(carVersion, requiredDetails)
        );

        return savedDefaulter.getId();
    }

    public Long createCustomizer(Long defaulterId, Long clientId) {
        CarConfigurationDefaulter defaulter = defaulterRepository.findById(defaulterId)
                .orElseThrow(() -> new DefaulterNotFoundException(defaulterId));
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));

        CarConfigurationCustomizer customizer = defaulter.create(user);
        customizer = customizerRepository.save(customizer);

        return customizer.getId();
    }

    public Long createCarConfiguration(Long customizerId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));

        CarConfiguration savedCarConfiguration = configurationRepository.save(customizer.build());

        return savedCarConfiguration.getId();
    }

    public Optional<CarConfigurationDefaulter> findDefaulterByCarVersionId(Long carVersionId) {
        return defaulterRepository.findByCarVersionId(carVersionId);
    }

    public Optional<CarConfigurationCustomizer> findCustomizerById(Long customizerId) {
        return customizerRepository.findById(customizerId);
    }

    public Optional<CarConfiguration> findConfigurationById(Long configurationId) {
        return configurationRepository.findById(configurationId);
    }

    public List<CarConfigurationDefaulter> findAllDefaulters() {
        return defaulterRepository.findAll();
    }

    public List<CarConfigurationCustomizer> findAllCustomizers() {
        return customizerRepository.findAll();
    }

    public List<CarConfiguration> findAllConfigurations() {
        return configurationRepository.findAll();
    }

    public void addDefaultDetail(Long defaulterId, Long detailId) {
        CarConfigurationDefaulter defaulter = defaulterRepository.findById(defaulterId)
                .orElseThrow(() -> new DefaulterNotFoundException(defaulterId));
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        defaulter.addRequiredDetail(detail);
    }

    public void selectCustomRequiredDetail(Long customizerId, Long detailId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        customizer.selectRequiredDetail(detail);
    }

    public void selectCustomOptionalDetail(Long customizerId, Long detailId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        customizer.selectOptionalDetail(detail);
    }

    public void rejectCustomOptionalDetail(Long customizerId, Long detailId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        customizer.rejectOptionalDetail(detail);
    }

    public void deleteDefaulterByCarId(Long carVersionId) {
        defaulterRepository.deleteByCarVersionId(carVersionId);
    }

    public void deleteCustomizerById(Long customizerId) {
        customizerRepository.deleteById(customizerId);
    }

    public void deleteConfigurationById(Long configurationId) {
        configurationRepository.deleteById(configurationId);
    }
}