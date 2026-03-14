package ru.lebedev.dealership.application.contracts.order.configuredcar;

import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.order.configuredcar.requests.UpdateConfigurationOrderRequest;

public interface ConfiguredCarOrderUseCase {
    long AddOrder(AddConfigurationOrderRequest request);

    void DeleteOrder(UpdateConfigurationOrderRequest request);

    void ApproveOrder(UpdateConfigurationOrderRequest request);

    void PayOrder(UpdateConfigurationOrderRequest request);

    void DeliverOrder(UpdateConfigurationOrderRequest request);

    void CompleteOrder(UpdateConfigurationOrderRequest request);

    void CancelOrder(UpdateConfigurationOrderRequest request);
}