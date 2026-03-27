package ru.lebedev.dealership.domain.carconfiguration;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.Set;

@Entity
@Table(name = "car_configuration")
public class CarConfiguration extends BaseEntity {

    @ManyToMany
    @JoinTable(
            name = "car_configuration_details",
            joinColumns = @JoinColumn(name = "car_configuration_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    private Set<Detail> details;

    public CarConfiguration(Set<Detail> details) {
        this.details = details;
    }

    public Set<Detail> getDetails() {
        return details;
    }
}