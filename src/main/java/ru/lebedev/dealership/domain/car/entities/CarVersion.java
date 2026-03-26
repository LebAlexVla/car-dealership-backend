package ru.lebedev.dealership.domain.car.entities;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.Engine;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.List;

@Entity
@Table(name = "car_version")
public class CarVersion extends BaseEntity {
    @Column(name = "car_version_name", nullable = false)
    private String carVersionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_head_id", nullable = false)
    private CarHead carHead;

    @Embedded
    private Engine engine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GearboxType gearboxType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarDrive carDrive;

    @ElementCollection
    @CollectionTable(
            name = "car_version_colors",
            joinColumns = @JoinColumn(name = "car_version_id")
    )
    @Column(name = "color")
    private List<String> colors;

    @Embedded
    private Price price;

    protected CarVersion(CarHead carHead) {
        this.carHead = carHead;
    }

    public CarVersion(
            String name, CarHead carHead,
            Engine engine,
            GearboxType gearboxType,
            CarDrive carDrive,
            List<String> colors,
            Price price
    ) {
        this.carVersionName = name;
        this.carHead = carHead;
        this.engine = engine;
        this.gearboxType = gearboxType;
        this.carDrive = carDrive;
        this.colors = colors;
        this.price = price;
    }

    public String getCarVersionName() {
        return carVersionName;
    }

    public CarHead getCarHead() {
        return carHead;
    }

    public Engine getEngine() {
        return engine;
    }

    public GearboxType getGearboxType() {
        return gearboxType;
    }

    public CarDrive getCarDrive() {
        return carDrive;
    }

    public List<String> getColors() {
        return colors;
    }

    public Price getPrice() {
        return price;
    }
}