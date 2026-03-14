package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.ConfigurationOrderRepository;
import ru.lebedev.dealership.application.contracts.configurationorder.ConfigurationOrderUseCase;
import ru.lebedev.dealership.application.contracts.configurationorder.mappers.ConfigurationOrderInputDtoMapper;
import ru.lebedev.dealership.application.contracts.configurationorder.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.configurationorder.requests.UpdateConfigurationOrderRequest;
import ru.lebedev.dealership.domain.order.configuration.ConfigurationOrder;

public class ConfigurationOrderService implements ConfigurationOrderUseCase {
    private final ConfigurationOrderRepository configurationOrderRepository;

    public ConfigurationOrderService(ConfigurationOrderRepository configurationOrderRepository) {
        this.configurationOrderRepository = configurationOrderRepository;
    }

    @Override
    public long AddOrder(AddConfigurationOrderRequest request) {
        ConfigurationOrder configurationOrder = ConfigurationOrderInputDtoMapper.map(request.inputDto());
        configurationOrder = configurationOrderRepository.save(configurationOrder);

        return configurationOrder.getOrderId();
    }

    @Override
    public void DeleteOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        configurationOrderRepository.delete(configurationOrderId);
    }

    @Override
    public void ApproveOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfigurationOrder configurationOrder = configurationOrderRepository.findById(configurationOrderId);
        configurationOrder.approve();
        configurationOrderRepository.save(configurationOrder);
    }

    @Override
    public void PayOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfigurationOrder configurationOrder = configurationOrderRepository.findById(configurationOrderId);
        configurationOrder.pay();
        configurationOrderRepository.save(configurationOrder);
    }

    @Override
    public void DeliverOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfigurationOrder configurationOrder = configurationOrderRepository.findById(configurationOrderId);
        configurationOrder.deliver();
        configurationOrderRepository.save(configurationOrder);
    }

    @Override
    public void CompleteOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfigurationOrder configurationOrder = configurationOrderRepository.findById(configurationOrderId);
        configurationOrder.complete();
        configurationOrderRepository.save(configurationOrder);
    }

    @Override
    public void CancelOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfigurationOrder configurationOrder = configurationOrderRepository.findById(configurationOrderId);
        configurationOrder.cancel();
        configurationOrderRepository.save(configurationOrder);
    }
}
