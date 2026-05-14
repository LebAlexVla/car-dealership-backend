package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;

@Service("carConfigurationSecurityService")
public class CarConfigurationSecurityService {
    private final CarConfigurationCustomizerRepository customizerRepository;
    private final CarConfigurationRepository configurationRepository;
    private final CurrentUserService currentUserService;

    public CarConfigurationSecurityService(
            CarConfigurationCustomizerRepository customizerRepository,
            CarConfigurationRepository configurationRepository,
            CurrentUserService currentUserService
    ) {
        this.customizerRepository = customizerRepository;
        this.configurationRepository = configurationRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional(readOnly = true)
    public boolean isCustomizerOwner(Long customizerId) {
        Long currentUserId = currentUserService.getCurrentUserId();

        return customizerRepository.findById(customizerId)
                .map(customizer -> customizer.getClient().getId().equals(currentUserId))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean isConfigurationOwner(Long configurationId) {
        Long currentUserId = currentUserService.getCurrentUserId();

        return configurationRepository.findById(configurationId)
                .map(configuration -> configuration.getClient().getId().equals(currentUserId))
                .orElse(false);
    }
}