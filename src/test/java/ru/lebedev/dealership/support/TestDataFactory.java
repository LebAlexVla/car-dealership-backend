package ru.lebedev.dealership.support;

import ru.lebedev.dealership.domain.BaseEntity;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.Engine;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.car.vo.Price;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static User user(Long id) {
        User user = new User("John", UserType.CUSTOMER, "+100000000");
        setId(user, id);
        return user;
    }

    public static CarHead carHead(Long id) {
        CarHead carHead = new CarHead("Audi", "A6", BodyType.SEDAN);
        setId(carHead, id);
        return carHead;
    }

    public static CarVersion carVersion(Long id) {
        CarVersion carVersion = new CarVersion(
                "2.0 TFSI",
                carHead(11L),
                new Engine(FuelType.GASOLINE, new EnginePower(190L), new EngineCapacity(2.0)),
                GearboxType.AUTOMATIC,
                CarDrive.ALL_WHEEL,
                new ArrayList<>(List.of("black")),
                new Price(BigDecimal.valueOf(2_000_000L))
        );
        setId(carVersion, id);
        return carVersion;
    }

    public static Detail detail(Long id, String name, String type, CarVersion compatibleCar) {
        Set<CarVersion> compatible = new HashSet<>();
        if (compatibleCar != null) {
            compatible.add(compatibleCar);
        }
        Detail detail = new Detail(name, type, new Price(BigDecimal.valueOf(10_000L)), compatible);
        setId(detail, id);
        return detail;
    }

    public static void setId(BaseEntity entity, Long id) {
        try {
            Field idField = BaseEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
