package ru.lebedev.dealership;

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
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static User user(Long id) {
        return customer(id);
    }

    public static User customer() {
        return customer(1L);
    }

    public static User customer(Long id) {
        User user = new User(
                "customer-keycloak-id-" + id,
                "Customer One",
                UserType.CUSTOMER,
                "+70000000001"
        );
        setId(user, id);
        return user;
    }

    public static User manager() {
        return manager(2L);
    }

    public static User manager(Long id) {
        User user = new User(
                "manager-keycloak-id-" + id,
                "Manager One",
                UserType.MANAGER,
                "+70000000002"
        );
        setId(user, id);
        return user;
    }

    public static User warehouseAdmin() {
        return warehouseAdmin(3L);
    }

    public static User warehouseAdmin(Long id) {
        User user = new User(
                "warehouse-keycloak-id-" + id,
                "Warehouse One",
                UserType.STORAGE_ADMIN,
                "+70000000003"
        );
        setId(user, id);
        return user;
    }

    public static User systemAdmin() {
        return systemAdmin(4L);
    }

    public static User systemAdmin(Long id) {
        User user = new User(
                "admin-keycloak-id-" + id,
                "Admin One",
                UserType.SYSTEM_ADMINISTRATOR,
                "+70000000004"
        );
        setId(user, id);
        return user;
    }

    public static CarHead carHead() {
        return carHead(1L);
    }

    public static CarHead carHead(Long id) {
        CarHead carHead = new CarHead("Audi", "A6", BodyType.SEDAN);
        setId(carHead, id);
        return carHead;
    }

    public static Engine engine() {
        return new Engine(
                FuelType.GASOLINE,
                new EnginePower(249L),
                new EngineCapacity(2.0)
        );
    }

    public static Price price() {
        return new Price(BigDecimal.valueOf(2_000_000));
    }

    public static CarVersion carVersion() {
        return carVersion(1L);
    }

    public static CarVersion carVersion(Long id) {
        CarVersion carVersion = new CarVersion(
                "Audi A6 45 TFSI",
                carHead(),
                engine(),
                GearboxType.AUTOMATIC,
                CarDrive.ALL_WHEEL,
                new ArrayList<>(List.of("BLACK", "WHITE")),
                price()
        );
        setId(carVersion, id);
        return carVersion;
    }

    public static CarVersion carVersion(Long id, CarHead carHead) {
        CarVersion carVersion = new CarVersion(
                "Audi A6 45 TFSI",
                carHead,
                engine(),
                GearboxType.AUTOMATIC,
                CarDrive.ALL_WHEEL,
                new ArrayList<>(List.of("BLACK", "WHITE")),
                price()
        );
        setId(carVersion, id);
        return carVersion;
    }

    public static CarVersion testDriveAvailableCarVersion() {
        return testDriveAvailableCarVersion(1L);
    }

    public static CarVersion testDriveAvailableCarVersion(Long id) {
        CarVersion carVersion = carVersion(id);
        carVersion.setTestDriveAvailable(true);
        return carVersion;
    }

    public static Detail detail() {
        return detail(1L);
    }

    public static Detail detail(Long id) {
        Detail detail = new Detail(
                "Winter Package",
                "PACKAGE",
                new Price(BigDecimal.valueOf(100_000)),
                new HashSet<>()
        );
        setId(detail, id);
        return detail;
    }

    public static Detail detail(Long id, String name, String type, CarVersion compatibleCarVersion) {
        Set<CarVersion> compatibleCars = compatibleCarVersion == null
                ? new HashSet<>()
                : new HashSet<>(Set.of(compatibleCarVersion));

        Detail detail = new Detail(
                name,
                type,
                new Price(BigDecimal.valueOf(10_000)),
                compatibleCars
        );
        setId(detail, id);
        return detail;
    }

    public static Detail compatibleDetail(CarVersion carVersion) {
        return compatibleDetail(1L, carVersion);
    }

    public static Detail compatibleDetail(Long id, CarVersion carVersion) {
        Detail detail = new Detail(
                "Sport Package",
                "PACKAGE",
                new Price(BigDecimal.valueOf(150_000)),
                new HashSet<>(Set.of(carVersion))
        );
        setId(detail, id);
        return detail;
    }

    public static StockOrder stockOrder() {
        return stockOrder(1L);
    }

    public static StockOrder stockOrder(Long id) {
        StockOrder order = new StockOrder(customer(), carVersion());
        setId(order, id);
        return order;
    }

    public static StockOrder stockOrder(Long id, User client) {
        StockOrder order = new StockOrder(client, carVersion());
        setId(order, id);
        return order;
    }

    public static StockOrder stockOrder(Long id, User client, CarVersion carVersion) {
        StockOrder order = new StockOrder(client, carVersion);
        setId(order, id);
        return order;
    }

    public static CarConfigurationDefaulter carConfigurationDefaulter() {
        return carConfigurationDefaulter(1L);
    }

    public static CarConfigurationDefaulter carConfigurationDefaulter(Long id) {
        CarVersion carVersion = carVersion();
        CarConfigurationDefaulter defaulter = new CarConfigurationDefaulter(
                carVersion,
                new HashSet<>(Set.of(compatibleDetail(carVersion)))
        );
        setId(defaulter, id);
        return defaulter;
    }

    public static CarConfigurationDefaulter carConfigurationDefaulter(
            Long id,
            CarVersion carVersion,
            Set<Detail> requiredDetails
    ) {
        CarConfigurationDefaulter defaulter = new CarConfigurationDefaulter(
                carVersion,
                new HashSet<>(requiredDetails)
        );
        setId(defaulter, id);
        return defaulter;
    }

    public static CarConfigurationCustomizer carConfigurationCustomizer() {
        return carConfigurationCustomizer(1L);
    }

    public static CarConfigurationCustomizer carConfigurationCustomizer(Long id) {
        CarVersion carVersion = carVersion();
        CarConfigurationCustomizer customizer = new CarConfigurationCustomizer(
                customer(),
                carVersion,
                new HashSet<>(Set.of(compatibleDetail(carVersion)))
        );
        setId(customizer, id);
        return customizer;
    }

    public static CarConfigurationCustomizer carConfigurationCustomizer(
            Long id,
            User client,
            CarVersion carVersion,
            Set<Detail> requiredDetails
    ) {
        CarConfigurationCustomizer customizer = new CarConfigurationCustomizer(
                client,
                carVersion,
                new HashSet<>(requiredDetails)
        );
        setId(customizer, id);
        return customizer;
    }

    public static CarConfiguration carConfiguration() {
        return carConfiguration(1L);
    }

    public static CarConfiguration carConfiguration(Long id) {
        CarVersion carVersion = carVersion();
        CarConfiguration configuration = new CarConfiguration(
                customer(),
                carVersion,
                new HashSet<>(Set.of(compatibleDetail(carVersion)))
        );
        setId(configuration, id);
        return configuration;
    }

    public static CarConfiguration carConfiguration(
            Long id,
            User client,
            CarVersion carVersion,
            Set<Detail> details
    ) {
        CarConfiguration configuration = new CarConfiguration(
                client,
                carVersion,
                new HashSet<>(details)
        );
        setId(configuration, id);
        return configuration;
    }

    public static ConfiguredCarOrder configuredCarOrder() {
        return configuredCarOrder(1L);
    }

    public static ConfiguredCarOrder configuredCarOrder(Long id) {
        ConfiguredCarOrder order = new ConfiguredCarOrder(carConfiguration());
        setId(order, id);
        return order;
    }

    public static ConfiguredCarOrder configuredCarOrder(Long id, CarConfiguration configuration) {
        ConfiguredCarOrder order = new ConfiguredCarOrder(configuration);
        setId(order, id);
        return order;
    }

    public static TestDrive testDrive() {
        return testDrive(1L);
    }

    public static TestDrive testDrive(Long id) {
        TestDrive testDrive = new TestDrive(
                customer(),
                testDriveAvailableCarVersion(),
                LocalDateTime.of(2026, 6, 1, 12, 0)
        );
        setId(testDrive, id);
        return testDrive;
    }

    public static TestDrive testDrive(Long id, User client) {
        TestDrive testDrive = new TestDrive(
                client,
                testDriveAvailableCarVersion(),
                LocalDateTime.of(2026, 6, 1, 12, 0)
        );
        setId(testDrive, id);
        return testDrive;
    }

    public static TestDrive testDrive(Long id, User client, CarVersion carVersion) {
        TestDrive testDrive = new TestDrive(
                client,
                carVersion,
                LocalDateTime.of(2026, 6, 1, 12, 0)
        );
        setId(testDrive, id);
        return testDrive;
    }

    public static TestDrive testDrive(Long id, User client, CarVersion carVersion, LocalDateTime dateTime) {
        TestDrive testDrive = new TestDrive(client, carVersion, dateTime);
        setId(testDrive, id);
        return testDrive;
    }

    public static <T extends BaseEntity> T withId(T entity, Long id) {
        setId(entity, id);
        return entity;
    }

    private static void setId(BaseEntity entity, Long id) {
        try {
            Field idField = BaseEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Failed to set entity id for test data", ex);
        }
    }
}