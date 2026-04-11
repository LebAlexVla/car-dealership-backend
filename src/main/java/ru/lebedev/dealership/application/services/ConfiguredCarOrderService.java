package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
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
    public final ConfiguredCarOrderRepository configuredCarOrderRepository;
    public final CarConfigurationRepository carConfigurationRepository;

    public ConfiguredCarOrderService(ConfiguredCarOrderRepository configuredCarOrderRepository, CarConfigurationRepository carConfigurationRepository) {
        this.configuredCarOrderRepository = configuredCarOrderRepository;
        this.carConfigurationRepository = carConfigurationRepository;
    }

    public Long create(Long carConfigurationId) {
        CarConfiguration carConfiguration = carConfigurationRepository.findById(carConfigurationId)
                .orElseThrow(() -> new CarConfigurationNotFoundException(carConfigurationId));
        ConfiguredCarOrder savedOrder = configuredCarOrderRepository.save(new ConfiguredCarOrder(carConfiguration));

        return savedOrder.getId();
    }

    public Optional<ConfiguredCarOrder> findById(Long orderId) {
        return configuredCarOrderRepository.findById(orderId);
    }

    public Optional<ConfiguredCarOrder> findByClientId(Long clientId) {
        return configuredCarOrderRepository.findByConfigurationClientId(clientId);
    }

    public List<ConfiguredCarOrder> findAll() {
        return configuredCarOrderRepository.findAll();
    }

    public void approveOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));
        order.approve();
    }

    public void payOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));
        order.pay();
    }

    public void deliverOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));
        order.deliver();
    }

    public void completeOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));
        order.complete();
    }

    public void cancelOrder(Long orderId) {
        ConfiguredCarOrder order = configuredCarOrderRepository.findById(orderId)
                .orElseThrow(() -> new ConfiguredCarOrderNotFoundException(orderId));
        order.cancel();
    }

    public void deleteById(Long orderId) {
        configuredCarOrderRepository.deleteById(orderId);
    }
}