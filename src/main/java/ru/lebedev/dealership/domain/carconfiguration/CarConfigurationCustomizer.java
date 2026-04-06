package ru.lebedev.dealership.domain.carconfiguration;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.exceptions.IncompatibleDetailException;
import ru.lebedev.dealership.domain.exceptions.NoSuchOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongOptionalDetailException;
import ru.lebedev.dealership.domain.exceptions.WrongRequiredDetailException;
import ru.lebedev.dealership.domain.user.User;

import java.util.HashSet;
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
    private Set<Detail> requiredDetails = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "car_configuration_customizer_optional_details",
            joinColumns = @JoinColumn(name = "car_configuration_customizer_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    private final Set<Detail> optionalDetails = new HashSet<>();

    protected CarConfigurationCustomizer() {
    }

    public CarConfigurationCustomizer(User client, CarVersion carVersion, Set<Detail> requiredDetails) {
        this.client = client;
        this.carVersion = carVersion;
        this.requiredDetails = requiredDetails;
    }

    public void selectRequiredDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersion)) {
            throw new IncompatibleDetailException(
                    detail.getName() + " is not compatible to the car with " + carVersion.getName()
            );
        }

        boolean isRequiredDetail = requiredDetails.stream()
                .anyMatch(required -> required.getType().equals(detail.getType()));
        if (!isRequiredDetail) {
            throw new WrongRequiredDetailException(
                    detail.getType() + " is not a required detail"
            );
        }

        requiredDetails.removeIf(existing -> existing.getType().equals(detail.getType()));
        requiredDetails.add(detail);
    }

    public void selectOptionalDetail(Detail detail) {
        if (!detail.checkCompatibility(carVersion)) {
            throw new IncompatibleDetailException(
                    detail.getName() + " is not compatible to the car with id: " + carVersion.getName()
            );
        }

        boolean isRequiredDetail = requiredDetails.stream()
                .anyMatch(required -> required.getType().equals(detail.getType()));
        if (isRequiredDetail) {
            throw new WrongOptionalDetailException(
                    detail.getType() + " is not an optional detail"
            );
        }

        optionalDetails.removeIf(existing -> existing.getType().equals(detail.getType()));
        optionalDetails.add(detail);
    }

    public void rejectOptionalDetail(Detail detail) {
        if (!optionalDetails.contains(detail)) {
            throw new NoSuchOptionalDetailException(detail.getType(), detail.getName());
        }

        optionalDetails.remove(detail);
    }

    public CarConfiguration build() {
        Set<Detail> details = new HashSet<>();
        details.addAll(requiredDetails);
        details.addAll(optionalDetails);

        return new CarConfiguration(client, carVersion, details);
    }

    public User getClient() {
        return client;
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }

    public Set<Detail> getRequiredDetails() {
        return new HashSet<>(requiredDetails);
    }

    public Set<Detail> getOptionalDetails() {
        return new HashSet<>(optionalDetails);
    }
}