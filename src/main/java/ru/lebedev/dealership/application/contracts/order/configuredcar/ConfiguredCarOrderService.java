package ru.lebedev.dealership.application.contracts.order.configuredcar;

import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.UpdateConfigurationOrderRequest;

public interface ConfiguredCarOrderService {
    Long addOrder(AddConfigurationOrderRequest request);

    void deleteOrder(UpdateConfigurationOrderRequest request);

    void approveOrder(UpdateConfigurationOrderRequest request);

    void payOrder(UpdateConfigurationOrderRequest request);

    void deliverOrder(UpdateConfigurationOrderRequest request);

    void completeOrder(UpdateConfigurationOrderRequest request);

    void cancelOrder(UpdateConfigurationOrderRequest request);
}