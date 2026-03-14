package ru.lebedev.dealership.application.contracts.carconfiguration;

import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;

public interface CarConfigurationService {
    long AddDefaulter(AddCarConfigurationDefaulterRequest request);

    void DeleteDefaulter(DeleteCarConfigurationDefaulterRequest request);

    long AddCustomizer(AddCarConfigurationCustomizerRequest request);

    void DeleteCustomizer(DeleteCarConfigurationCustomizerRequest request);

    void AddCustomRequiredDetail(AddCustomDetailRequest request);

    void AddCustomOptionalDetail(AddCustomDetailRequest request);

    long CreateCarConfiguration();
}
