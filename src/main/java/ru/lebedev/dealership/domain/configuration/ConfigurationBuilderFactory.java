package ru.lebedev.dealership.domain.configuration;

import ru.lebedev.dealership.domain.car.vo.CarVersionId;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationBuilderFactory {
    private final CarVersionId carVersionId;
    private final Map<DetailType, Detail> requiredDetails;

    public ConfigurationBuilderFactory(CarVersionId carVersionId, Map<DetailType, Detail> requiredDetails) {
        this.carVersionId = carVersionId;
        this.requiredDetails = requiredDetails;
    }

    public void addRequiredDetail(Detail detail) {
        requiredDetails.put(detail.type(), detail);
    }

    public void removeRequiredDetail(Detail detail) {
        requiredDetails.remove(detail.type());
    }

    public ConfigurationBuilder create() {
        return new ConfigurationBuilder(carVersionId, new HashMap<>(requiredDetails));
    }
}
