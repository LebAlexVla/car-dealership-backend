package ru.lebedev.dealership.domain.car.entities;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.enums.BodyType;

@Entity
@Table(name = "car_head")
public class CarHead extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false)
    private BodyType bodyType;

    protected CarHead() {}

    public CarHead(
            String brand,
            String model,
            BodyType bodyType
    ) {
        this.brand = brand;
        this.model = model;
        this.bodyType = bodyType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public BodyType getBodyType() {
        return bodyType;
    }
}