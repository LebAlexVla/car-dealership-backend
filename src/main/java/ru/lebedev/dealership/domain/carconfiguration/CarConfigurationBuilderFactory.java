package ru.lebedev.dealership.domain.carconfiguration;

import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;

import java.util.HashMap;
import java.util.Map;

public class CarConfigurationBuilderFactory {
    private final long carVersionId;
    private final Map<DetailType, Detail> requiredDetails;

    public CarConfigurationBuilderFactory(long carVersionId, Map<DetailType, Detail> requiredDetails) {
        this.carVersionId = carVersionId;
        this.requiredDetails = requiredDetails;
    }

    public void addRequiredDetail(Detail detail) {
        requiredDetails.put(detail.type(), detail);
    }

    public void removeRequiredDetail(Detail detail) {
        requiredDetails.remove(detail.type());
    }

    public CarConfigurationBuilder create(long clientId) {
        return new CarConfigurationBuilder(carVersionId, clientId, new HashMap<>(requiredDetails));
    }
}