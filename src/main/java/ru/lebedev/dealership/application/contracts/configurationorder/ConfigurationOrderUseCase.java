package ru.lebedev.dealership.application.contracts.configurationorder;

import ru.lebedev.dealership.application.contracts.configurationorder.requests.AddConfigurationOrderRequest;
import ru.lebedev.dealership.application.contracts.configurationorder.requests.UpdateConfigurationOrderRequest;

public interface ConfigurationOrderUseCase {
    long AddOrder(AddConfigurationOrderRequest request);

    void DeleteOrder(UpdateConfigurationOrderRequest request);

    void ApproveOrder(UpdateConfigurationOrderRequest request);

    void PayOrder(UpdateConfigurationOrderRequest request);

    void DeliverOrder(UpdateConfigurationOrderRequest request);

    void CompleteOrder(UpdateConfigurationOrderRequest request);

    void CancelOrder(UpdateConfigurationOrderRequest request);
}