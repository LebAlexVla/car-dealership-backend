package ru.lebedev.dealership.domain.carconfiguration;

import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.exceptions.IncompatibleDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongRequiredDetailException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CarConfigurationCustomizer {
    private final Long CarConfigurationCustomizerId;
    private final Long carVersionId;
    private final Long clientId;
    private final Map<DetailType, Long> requiredDetails;

    private final Map<DetailType, Long> optionalDetails = new HashMap<>();

    public CarConfigurationCustomizer(Long customCarConfigurationId, Long carVersionId, Long clientId, Map<DetailType, Long> requiredDetails) {
        this.CarConfigurationCustomizerId = customCarConfigurationId;
        this.carVersionId = carVersionId;
        this.clientId = clientId;
        this.requiredDetails = requiredDetails;
    }

    public CarConfigurationCustomizer withRequiredDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersionId)) {
            throw new IncompatibleDetailException(
                    detail.name() + " is not compatible to the car with id: " + carVersionId
            );
        }

        if (!requiredDetails.containsKey(detail.type())) {
            throw new WrongRequiredDetailException(
                    detail.type().name() + " is not a required detail"
            );
        }

        requiredDetails.put(detail.type(), detail.detailId());

        return this;
    }

    public CarConfigurationCustomizer withOptionalDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersionId)) {
            throw new IncompatibleDetailException(
                    detail.name() + " is not compatible to the car with id: " + carVersionId
            );
        }

        if (requiredDetails.containsKey(detail.type())) {
            throw new WrongOptionalDetailException(
                    detail.type().name() + " is not an optional detail"
            );
        }

        optionalDetails.put(detail.type(), detail.detailId());

        return this;
    }

    public CarConfiguration build(Long carConfigurationId) {
        Set<Long> details = new HashSet<>();
        details.addAll(requiredDetails.values());
        details.addAll(optionalDetails.values());

        return new CarConfiguration(carConfigurationId, details);
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getCustomCarConfigurationId() {
        return CarConfigurationCustomizerId;
    }
}