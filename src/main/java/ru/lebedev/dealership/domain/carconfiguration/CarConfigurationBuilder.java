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

public class CarConfigurationBuilder {
    private final long carVersionId;
    private final long clientId;
    private final Map<DetailType, Detail> requiredDetails;

    private final Map<DetailType, Detail> optionalDetails = new HashMap<>();

    public CarConfigurationBuilder(long carVersionId, long clientId, Map<DetailType, Detail> requiredDetails) {
        this.carVersionId = carVersionId;
        this.clientId = clientId;
        this.requiredDetails = requiredDetails;
    }

    public CarConfigurationBuilder withRequiredDetail(Detail detail) {
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

        requiredDetails.put(detail.type(), detail);

        return this;
    }

    public CarConfigurationBuilder withOptionalDetail(Detail detail) {
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

        optionalDetails.put(detail.type(), detail);

        return this;
    }

    public CarConfiguration Build() {
        Set<Detail> details = new HashSet<>();
        details.addAll(requiredDetails.values());
        details.addAll(optionalDetails.values());

        return new CarConfiguration(details);
    }

    public long getClientId() {
        return clientId;
    }
}
