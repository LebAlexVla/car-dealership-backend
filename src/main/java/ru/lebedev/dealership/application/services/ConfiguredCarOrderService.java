package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.ConfiguredCarOrderRepository;
import ru.lebedev.dealership.application.contracts.order.configuredcar.ConfiguredCarOrderUseCase;
import ru.lebedev.dealership.application.contracts.order.configuredcar.mappers.ConfiguredCarOrderInputDtoMapper;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.UpdateConfigurationOrderRequest;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

public class ConfiguredCarOrderService implements ConfiguredCarOrderUseCase {
    private final ConfiguredCarOrderRepository configuredCarOrderRepository;

    public ConfiguredCarOrderService(ConfiguredCarOrderRepository configuredCarOrderRepository) {
        this.configuredCarOrderRepository = configuredCarOrderRepository;
    }

    @Override
    public long AddOrder(AddConfigurationOrderRequest request) {
        ConfiguredCarOrder configuredCarOrder = ConfiguredCarOrderInputDtoMapper.map(request.inputDto());
        configuredCarOrder = configuredCarOrderRepository.save(configuredCarOrder);

        return configuredCarOrder.getOrderId();
    }

    @Override
    public void DeleteOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        configuredCarOrderRepository.delete(configurationOrderId);
    }

    @Override
    public void ApproveOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.approve();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void PayOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.pay();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void DeliverOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.deliver();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void CompleteOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.complete();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void CancelOrder(UpdateConfigurationOrderRequest request) {
        long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.cancel();
        configuredCarOrderRepository.save(configuredCarOrder);
    }
}
