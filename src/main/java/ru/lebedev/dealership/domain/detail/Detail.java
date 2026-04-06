package ru.lebedev.dealership.domain.detail;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "detail")
public class Detail extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Embedded
    private Price price;

    @ManyToMany
    @JoinTable(
            name = "detail_compatible_car_versions",
            joinColumns = @JoinColumn(name = "detail_id"),
            inverseJoinColumns = @JoinColumn(name = "car_version_id")
    )
    private Set<CarVersion> compatibleCars;

    protected Detail() {
    }

    public Detail(String name, String type, Price price, Set<CarVersion> compatibleCars) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.compatibleCars = (compatibleCars != null ? compatibleCars : new HashSet<>());
    }

    public boolean checkCompatibility(CarVersion carVersion) {
        return compatibleCars.contains(carVersion);
    }

    public void addCompatibleCar(CarVersion carVersion) {
        compatibleCars.add(carVersion);
    }

    public void removeCompatibleCar(CarVersion carVersion) {
        compatibleCars.remove(carVersion);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Price getPrice() {
        return price;
    }

    public Set<CarVersion> getCompatibleCars() {
        return compatibleCars;
    }
}