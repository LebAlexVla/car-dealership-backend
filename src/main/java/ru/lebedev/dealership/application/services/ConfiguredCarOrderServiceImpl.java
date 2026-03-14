package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.ConfiguredCarOrderRepository;
import ru.lebedev.dealership.application.contracts.order.configuredcar.mappers.ConfiguredCarOrderInputDtoMapper;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.UpdateConfigurationOrderRequest;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;

public class ConfiguredCarOrderServiceImpl implements ru.lebedev.dealership.application.contracts.order.configuredcar.ConfiguredCarOrderService {
    private final ConfiguredCarOrderRepository configuredCarOrderRepository;

    public ConfiguredCarOrderServiceImpl(ConfiguredCarOrderRepository configuredCarOrderRepository) {
        this.configuredCarOrderRepository = configuredCarOrderRepository;
    }

    @Override
    public Long addOrder(AddConfigurationOrderRequest request) {
        ConfiguredCarOrder configuredCarOrder = ConfiguredCarOrderInputDtoMapper.map(request.inputDto());
        configuredCarOrder = configuredCarOrderRepository.save(configuredCarOrder);

        return configuredCarOrder.getOrderId();
    }

    @Override
    public void deleteOrder(UpdateConfigurationOrderRequest request) {
        Long configurationOrderId = request.configurationOrderId();
        configuredCarOrderRepository.delete(configurationOrderId);
    }

    @Override
    public void approveOrder(UpdateConfigurationOrderRequest request) {
        Long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.approve();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void payOrder(UpdateConfigurationOrderRequest request) {
        Long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.pay();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void deliverOrder(UpdateConfigurationOrderRequest request) {
        Long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.deliver();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void completeOrder(UpdateConfigurationOrderRequest request) {
        Long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.complete();
        configuredCarOrderRepository.save(configuredCarOrder);
    }

    @Override
    public void cancelOrder(UpdateConfigurationOrderRequest request) {
        Long configurationOrderId = request.configurationOrderId();
        ConfiguredCarOrder configuredCarOrder = configuredCarOrderRepository.findById(configurationOrderId);
        configuredCarOrder.cancel();
        configuredCarOrderRepository.save(configuredCarOrder);
    }
}