package ru.lebedev.dealership.domain.carconfiguration;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.shared.vo.Price;
import ru.lebedev.dealership.domain.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car_configuration")
public class CarConfiguration extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_version_id", nullable = false)
    private CarVersion carVersion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "car_configuration_details",
            joinColumns = @JoinColumn(name = "car_configuration_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    private Set<Detail> details;

    protected CarConfiguration() {
    }

    public CarConfiguration(User client, CarVersion carVersion, Set<Detail> details) {
        this.client = client;
        this.carVersion = carVersion;
        this.details = details;
    }

    public Price countPrice() {
        Price price = carVersion.getPrice();
        for (var detail : details) {
            price = price.add(detail.getPrice());
        }

        return price;
    }

    public User getClient() {
        return client;
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }

    public Set<Detail> getDetails() {
        return new HashSet<>(details);
    }
}