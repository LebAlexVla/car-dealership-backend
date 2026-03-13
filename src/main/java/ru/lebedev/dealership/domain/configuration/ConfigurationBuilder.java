package ru.lebedev.dealership.domain.configuration;

import ru.lebedev.dealership.domain.car.vo.CarVersionId;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.exceptions.IncompatibleDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongRequiredDetailException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigurationBuilder {
    private final CarVersionId carVersionId;
    private final Map<DetailType, Detail> requiredDetails;

    private final Map<DetailType, Detail> optionalDetails = new HashMap<>();

    public ConfigurationBuilder(CarVersionId carVersionId, Map<DetailType, Detail> requiredDetails) {
        this.carVersionId = carVersionId;
        this.requiredDetails = requiredDetails;
    }

    public ConfigurationBuilder withRequiredDetail(Detail detail) {
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

    public ConfigurationBuilder withOptionalDetail(Detail detail) {
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

    public Configuration Build() {
        Set<Detail> details = new HashSet<>();
        details.addAll(requiredDetails.values());
        details.addAll(optionalDetails.values());

        return new Configuration(details);
    }
}
