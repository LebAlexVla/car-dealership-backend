package ru.lebedev.dealership.domain.carconfiguration;

import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;

import java.util.HashMap;
import java.util.Map;

public class CarConfigurationDefaulter {
    private final Long CarConfigurationDefaulterId;
    private final Long carVersionId;
    private final Map<DetailType, Long> requiredDetails;

    public CarConfigurationDefaulter(Long CarConfigurationDefaulterId, Long carVersionId, Map<DetailType, Long> requiredDetails) {
        this.CarConfigurationDefaulterId = CarConfigurationDefaulterId;
        this.carVersionId = carVersionId;
        this.requiredDetails = requiredDetails;
    }

    public void addRequiredDetail(Detail detail) {
        requiredDetails.put(detail.type(), detail.detailId());
    }

    public void removeRequiredDetail(DetailType detailType) {
        requiredDetails.remove(detailType);
    }

    public CarConfigurationCustomizer create(Long customCarConfigurationId, Long clientId) {
        return new CarConfigurationCustomizer(
                customCarConfigurationId,
                carVersionId,
                clientId,
                new HashMap<>(requiredDetails)
        );
    }

    public Long getCarConfigurationDefaulterId() {
        return CarConfigurationDefaulterId;
    }
}