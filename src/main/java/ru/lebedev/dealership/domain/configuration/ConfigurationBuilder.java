package ru.lebedev.dealership.domain.configuration;

import ru.lebedev.dealership.domain.car.entities.Detail;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.car.valueobjects.DetailType;

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
            throw new IllegalArgumentException("Incompatible detail");
        }

        if (!requiredDetails.containsKey(detail.getType())) {
            throw new IllegalArgumentException("There is no such required detail type");
        }

        requiredDetails.put(detail.getType(), detail);

        return this;
    }

    public ConfigurationBuilder withOptionalDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersionId)) {
            throw new IllegalArgumentException("Incompatible detail");
        }

        optionalDetails.put(detail.getType(), detail);

        return this;
    }

    public Configuration Build() {
        Set<Detail> details = new HashSet<>();
        details.addAll(requiredDetails.values());
        details.addAll(optionalDetails.values());

        return new Configuration(details);
    }
}
