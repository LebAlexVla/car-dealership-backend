package ru.lebedev.dealership.domain.carconfiguration;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.exceptions.IncompatibleDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongRequiredDetailException;
import ru.lebedev.dealership.domain.user.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "car_configuration_customizer")
public class CarConfigurationCustomizer extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_version_id")
    private CarVersion carVersion;

    @ManyToMany
    @JoinTable(
            name = "car_configuration_customizer_required_details",
            joinColumns = @JoinColumn(name = "car_configuration_customizer_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    @MapKey(name = "type")
    private Map<String, Detail> requiredDetails = new HashMap<>();

    @ManyToMany
    @JoinTable(
            name = "car_configuration_customizer_optional_details",
            joinColumns = @JoinColumn(name = "car_configuration_customizer_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    @MapKey(name = "type")
    private Map<String, Detail> optionalDetails = new HashMap<>();

    protected CarConfigurationCustomizer() {
    }

    public CarConfigurationCustomizer(User client, CarVersion carVersion, Map<String, Detail> requiredDetails) {
        this.client = client;
        this.carVersion = carVersion;
        this.requiredDetails = requiredDetails;
    }

    public CarConfigurationCustomizer withRequiredDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersion)) {
            throw new IncompatibleDetailException(
                    detail.getName() + " is not compatible to the car with " + carVersion.getCarVersionName()
            );
        }

        if (!requiredDetails.containsKey(detail.getType())) {
            throw new WrongRequiredDetailException(
                    detail.getType() + " is not a required detail"
            );
        }

        requiredDetails.put(detail.getType(), detail);

        return this;
    }

    public CarConfigurationCustomizer withOptionalDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersion)) {
            throw new IncompatibleDetailException(
                    detail.getName() + " is not compatible to the car with id: " + carVersion.getCarVersionName()
            );
        }

        if (requiredDetails.containsKey(detail.getType())) {
            throw new WrongOptionalDetailException(
                    detail.getType() + " is not an optional detail"
            );
        }

        optionalDetails.put(detail.getType(), detail);

        return this;
    }

    public CarConfiguration build() {
        Set<Detail> details = new HashSet<>();
        details.addAll(requiredDetails.values());
        details.addAll(optionalDetails.values());

        return new CarConfiguration(client, carVersion, details);
    }

    public User getClient() {
        return client;
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }

    public Map<String, Detail> getRequiredDetails() {
        return new HashMap<>(requiredDetails);
    }

    public Map<String, Detail> getOptionalDetails() {
        return new HashMap<>(optionalDetails);
    }
}