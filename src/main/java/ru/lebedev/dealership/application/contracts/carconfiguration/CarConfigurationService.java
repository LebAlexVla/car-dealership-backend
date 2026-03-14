package ru.lebedev.dealership.application.contracts.carconfiguration;

import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;

public interface CarConfigurationService {
    Long addDefaulter(AddCarConfigurationDefaulterRequest request);

    void deleteDefaulter(DeleteCarConfigurationDefaulterRequest request);

    Long addCustomizer(AddCarConfigurationCustomizerRequest request);

    void deleteCustomizer(DeleteCarConfigurationCustomizerRequest request);

    void addCustomRequiredDetail(AddCustomDetailRequest request);

    void addCustomOptionalDetail(AddCustomDetailRequest request);

    Long createCarConfiguration(CreateCarConfigurationRequest request);
}
