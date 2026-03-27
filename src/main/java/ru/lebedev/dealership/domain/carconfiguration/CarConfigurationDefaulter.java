package ru.lebedev.dealership.domain.carconfiguration;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.exceptions.DuplicateDefaultDetailTypeException;
import ru.lebedev.dealership.domain.user.User;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "car_configuration_defaulter")
public class CarConfigurationDefaulter extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_version_id", nullable = false)
    private CarVersion carVersion;

    @ManyToMany
    @JoinTable(
            name = "configuration_defaulter_required_details",
            joinColumns = @JoinColumn(name = "car_configuration_defaulter_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    @MapKey(name = "type")
    private Map<String, Detail> requiredDetails = new HashMap<>();

    protected CarConfigurationDefaulter() {
    }

    public CarConfigurationDefaulter(CarVersion carVersion, Map<String, Detail> requiredDetails) {
        this.carVersion = carVersion;
        this.requiredDetails = requiredDetails;
    }

    public void addRequiredDetail(Detail detail) {
        if (requiredDetails.containsKey(detail.getType())) {
            throw new DuplicateDefaultDetailTypeException(
                    detail.getType() +
                            " already added to the default configuration of the " +
                            carVersion.getCarVersionName()
            );
        }

        requiredDetails.put(detail.getType(), detail);
    }

    public void removeRequiredDetail(String detailType) {
        requiredDetails.remove(detailType);
    }

    public CarConfigurationCustomizer create(User client) {
        return new CarConfigurationCustomizer(
                client,
                carVersion,
                new HashMap<>(requiredDetails)
        );
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }

    public Map<String, Detail> getRequiredDetails() {
        return new HashMap<>(requiredDetails);
    }
}