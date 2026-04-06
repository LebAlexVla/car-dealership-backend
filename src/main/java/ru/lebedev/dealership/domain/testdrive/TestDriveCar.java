package ru.lebedev.dealership.domain.testdrive;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

@Entity
@Table(name = "test_drive_car")
public class TestDriveCar extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false, unique = true)
    private CarVersion carVersion;

    protected TestDriveCar() {
    }

    public TestDriveCar(CarVersion carVersion) {
        this.carVersion = carVersion;
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }
}