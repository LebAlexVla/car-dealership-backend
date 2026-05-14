package ru.lebedev.dealership.application.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.application.exceptions.*;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationDefaulterRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.DetailRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

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

    public CarConfigurationService(
            CarConfigurationDefaulterRepository defaulterRepository,
            CarConfigurationCustomizerRepository customizerRepository,
            CarConfigurationRepository configurationRepository,
            UserRepository userRepository,
            CarVersionRepository carVersionRepository,
            DetailRepository detailRepository
    ) {
        this.defaulterRepository = defaulterRepository;
        this.customizerRepository = customizerRepository;
        this.configurationRepository = configurationRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
        this.detailRepository = detailRepository;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public Long createDefaulter(Long carVersionId, Set<Long> requiredDetailsIds) {
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));

        Set<Detail> requiredDetails = new HashSet<>(detailRepository.findAllById(requiredDetailsIds));

        CarConfigurationDefaulter savedDefaulter = defaulterRepository.save(
                new CarConfigurationDefaulter(carVersion, requiredDetails)
        );

        return savedDefaulter.getId();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Long createCustomizer(Long defaulterId, Long clientId) {
        CarConfigurationDefaulter defaulter = defaulterRepository.findById(defaulterId)
                .orElseThrow(() -> new DefaulterNotFoundException(defaulterId));

        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));

        CarConfigurationCustomizer customizer = defaulter.create(user);
        CarConfigurationCustomizer savedCustomizer = customizerRepository.save(customizer);

        return savedCustomizer.getId();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @carConfigurationSecurityService.isCustomizerOwner(#customizerId)")
    public Long createCarConfiguration(Long customizerId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));

        CarConfiguration savedCarConfiguration = configurationRepository.save(customizer.build());

        return savedCarConfiguration.getId();
    }

    @Transactional(readOnly = true)
    public Optional<CarConfigurationDefaulter> findDefaulterByCarVersionId(Long carVersionId) {
        return defaulterRepository.findByCarVersionId(carVersionId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @carConfigurationSecurityService.isCustomizerOwner(#customizerId)")
    public Optional<CarConfigurationCustomizer> findCustomizerById(Long customizerId) {
        return customizerRepository.findById(customizerId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @carConfigurationSecurityService.isConfigurationOwner(#configurationId)")
    public Optional<CarConfiguration> findConfigurationById(Long configurationId) {
        return configurationRepository.findById(configurationId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<CarConfigurationDefaulter> findAllDefaulters() {
        return defaulterRepository.findAll();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<CarConfigurationCustomizer> findAllCustomizers() {
        return customizerRepository.findAll();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<CarConfiguration> findAllConfigurations() {
        return configurationRepository.findAll();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void addDefaultDetail(Long defaulterId, Long detailId) {
        CarConfigurationDefaulter defaulter = defaulterRepository.findById(defaulterId)
                .orElseThrow(() -> new DefaulterNotFoundException(defaulterId));

        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        defaulter.addRequiredDetail(detail);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @carConfigurationSecurityService.isCustomizerOwner(#customizerId)")
    public void selectCustomRequiredDetail(Long customizerId, Long detailId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));

        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        customizer.selectRequiredDetail(detail);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @carConfigurationSecurityService.isCustomizerOwner(#customizerId)")
    public void selectCustomOptionalDetail(Long customizerId, Long detailId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));

        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        customizer.selectOptionalDetail(detail);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @carConfigurationSecurityService.isCustomizerOwner(#customizerId)")
    public void rejectCustomOptionalDetail(Long customizerId, Long detailId) {
        CarConfigurationCustomizer customizer = customizerRepository.findById(customizerId)
                .orElseThrow(() -> new CustomizerNotFoundException(customizerId));

        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new DetailNotFoundException(detailId));

        customizer.rejectOptionalDetail(detail);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void deleteDefaulterByCarId(Long carVersionId) {
        defaulterRepository.deleteByCarVersionId(carVersionId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @carConfigurationSecurityService.isCustomizerOwner(#customizerId)")
    public void deleteCustomizerById(Long customizerId) {
        customizerRepository.deleteById(customizerId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @carConfigurationSecurityService.isConfigurationOwner(#configurationId)")
    public void deleteConfigurationById(Long configurationId) {
        configurationRepository.deleteById(configurationId);
    }
}