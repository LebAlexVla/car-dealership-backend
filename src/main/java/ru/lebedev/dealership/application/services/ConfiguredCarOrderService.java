package ru.lebedev.dealership.application.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.application.exceptions.CarConfigurationNotFoundException;
import ru.lebedev.dealership.application.exceptions.ConfiguredCarOrderNotFoundException;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.ConfiguredCarOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguredCarOrderService {
    private final ConfiguredCarOrderRepository configuredCarOrderRepository;
    private final CarConfigurationRepository carConfigurationRepository;
    private final CurrentUserService currentUserService;

    public ConfiguredCarOrderService(
            ConfiguredCarOrderRepository configuredCarOrderRepository,
            CarConfigurationRepository carConfigurationRepository,
            CurrentUserService currentUserService
    ) {
        this.configuredCarOrderRepository = configuredCarOrderRepository;
        this.carConfigurationRepository = carConfigurationRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Long create(Long carConfigurationId) {
        CarConfiguration carConfiguration = carConfigurationRepository.findById(carConfigurationId)
                .orElseThrow(() -> new CarConfigurationNotFoundException(carConfigurationId));

        if (!currentUserService.hasRole("ADMIN")
                && !carConfiguration.getClient().getId().equals(currentUserService.getCurrentUserId())) {
            throw new CarConfigurationNotFoundException(carConfigurationId);
        }

        ConfiguredCarOrder savedOrder = configuredCarOrderRepository.save(new ConfiguredCarOrder(carConfiguration));

        return savedOrder.getId();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderSecurityService.isConfiguredOrderOwner(#orderId)")
    public Optional<ConfiguredCarOrder> findById(Long orderId) {
        return configuredCarOrderRepository.findById(orderId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<ConfiguredCarOrder> findByClientId(Long clientId) {
        if (currentUserService.hasRole("ADMIN") || currentUserService.hasRole("MANAGER")) {
            return configuredCarOrderRepository.findByConfigurationClientId(clientId);
        }

        Long currentUserId = currentUserService.getCurrentUserId();

        if (!currentUserId.equals(clientId)) {
            return List.of();
        }

        return configuredCarOrderRepository.findByConfigurationClientId(clientId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<ConfiguredCarOrder> findAll() {
        if (currentUserService.hasRole("ADMIN") || currentUserService.hasRole("MANAGER")) {
            return configuredCarOrderRepository.findAll();
        }

        return configuredCarOrderRepository.findByConfigurationClientId(currentUserService.getCurrentUserId());
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'WAREHOUSE_ADMIN', 'ADMIN')")
    public void approveOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));

        order.approve();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderSecurityService.isConfiguredOrderOwner(#orderId)")
    public void payOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));

        order.pay();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN', 'ADMIN')")
    public void deliverOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));

        order.deliver();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void completeOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));

        order.complete();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderSecurityService.isConfiguredOrderOwner(#orderId)")
    public void cancelOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));

        order.cancel();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long orderId) {
        configuredCarOrderRepository.deleteById(orderId);
    }
}