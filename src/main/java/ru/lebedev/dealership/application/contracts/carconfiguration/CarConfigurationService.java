package ru.lebedev.dealership.application.contracts.carconfiguration;

import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;

public interface CarConfigurationService {
    long addDefaulter(AddCarConfigurationDefaulterRequest request);

    void deleteDefaulter(DeleteCarConfigurationDefaulterRequest request);

    long addCustomizer(AddCarConfigurationCustomizerRequest request);

    void deleteCustomizer(DeleteCarConfigurationCustomizerRequest request);

    void addCustomRequiredDetail(AddCustomDetailRequest request);

    void addCustomOptionalDetail(AddCustomDetailRequest request);

    long createCarConfiguration();
}
