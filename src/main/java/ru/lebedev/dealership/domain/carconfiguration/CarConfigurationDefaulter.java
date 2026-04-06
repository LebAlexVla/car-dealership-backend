package ru.lebedev.dealership.domain.carconfiguration;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.exceptions.DuplicateDefaultDetailTypeException;
import ru.lebedev.dealership.domain.user.User;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Detail> requiredDetails = new HashSet<>();

    protected CarConfigurationDefaulter() {
    }

    public CarConfigurationDefaulter(CarVersion carVersion, Set<Detail> requiredDetails) {
        this.carVersion = carVersion;
        this.requiredDetails = requiredDetails;
    }

    public void addRequiredDetail(Detail detail) {

        requiredDetails.stream()
                .filter(requiredDetail -> requiredDetail.getType().equals(detail.getType()))
                .findFirst()
                .ifPresent(requiredDetail -> {
                    throw new DuplicateDefaultDetailTypeException(
                            detail.getType() +
                                    " already added to the default configuration of the " +
                                    carVersion.getName()
                    );
                });

        requiredDetails.add(detail);
    }

    public void removeRequiredDetail(String detailType) {
        requiredDetails.removeIf(detail -> detail.getType().equals(detailType));
    }

    public CarConfigurationCustomizer create(User client) {
        return new CarConfigurationCustomizer(
                client,
                carVersion,
                new HashSet<>(requiredDetails)
        );
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }

    public Set<Detail> getRequiredDetails() {
        return new HashSet<>(requiredDetails);
    }
}