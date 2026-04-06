package ru.lebedev.dealership.domain.car.entities;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.Engine;
import ru.lebedev.dealership.domain.car.vo.Price;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car_version")
public class CarVersion extends BaseEntity {
    @Column(name = "car_version_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_head_id", nullable = false)
    private CarHead carHead;

    @Embedded
    private Engine engine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GearboxType gearboxType;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_drive", nullable = false)
    private CarDrive carDrive;

    @ElementCollection
    @CollectionTable(
            name = "car_version_colors",
            joinColumns = @JoinColumn(name = "car_version_id")
    )
    @Column(name = "color")
    private List<String> colors = new ArrayList<>();

    @Embedded
    private Price price;

    @Column(name = "is_test_drive_availible", nullable = false)
    private boolean testDriveAvailable = false;

    protected CarVersion() {
    }

    public CarVersion(
            String name,
            CarHead carHead,
            Engine engine,
            GearboxType gearboxType,
            CarDrive carDrive,
            List<String> colors,
            Price price
    ) {
        this.name = name;
        this.carHead = carHead;
        this.engine = engine;
        this.gearboxType = gearboxType;
        this.carDrive = carDrive;
        this.colors = colors;
        this.price = price;
    }

    public void addColors(List<String> colors) {
        if (colors != null) {
            this.colors.addAll(colors);
        }
    }

    public void assignCarHead(CarHead carHead) {
        this.carHead = carHead;
    }

    public String getName() {
        return name;
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
        return List.copyOf(colors);
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public boolean isTestDriveAvailable() {
        return testDriveAvailable;
    }

    public void setTestDriveAvailable(boolean testDriveAvailable) {
        this.testDriveAvailable = testDriveAvailable;
    }
}