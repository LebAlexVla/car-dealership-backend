package ru.lebedev.dealership.domain.testdrive;

import jakarta.persistence.*;
import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_drive")
public class TestDrive extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_version_id", nullable = false)
    private CarVersion carVersion;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    protected TestDrive() {
    }

    public TestDrive(
            User client, CarVersion carVersion, LocalDateTime dateTime
    ) {
        this.client = client;
        this.carVersion = carVersion;
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public User getClient() {
        return client;
    }

    public CarVersion getCarVersion() {
        return carVersion;
    }
}