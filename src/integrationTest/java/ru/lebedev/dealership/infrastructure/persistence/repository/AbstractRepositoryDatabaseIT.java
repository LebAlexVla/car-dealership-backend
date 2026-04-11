package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
@Transactional
public abstract class AbstractRepositoryDatabaseIT {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CarHeadRepository carHeadRepository;

    @Autowired
    protected CarVersionRepository carVersionRepository;

    @Autowired
    protected DetailRepository detailRepository;

    protected User createUser(String phone) {
        return userRepository.save(new User("Client " + phone, UserType.CUSTOMER, phone));
    }

    protected CarVersion createCarVersion(String name, boolean testDriveAvailable) {
        CarHead head = carHeadRepository.save(new CarHead("Audi", "MODEL-" + name, BodyType.SEDAN));
        CarVersion version = new CarVersion(
                name,
                head,
                new Engine(FuelType.GASOLINE, new EnginePower(190L), new EngineCapacity(2.0)),
                GearboxType.AUTOMATIC,
                CarDrive.ALL_WHEEL,
                List.of("black"),
                new Price(BigDecimal.valueOf(2_000_000))
        );
        version.setTestDriveAvailable(testDriveAvailable);
        return carVersionRepository.save(version);
    }

    protected Detail createDetail(String name, String type, CarVersion compatibleCarVersion) {
        return detailRepository.save(
                new Detail(
                        name,
                        type,
                        new Price(BigDecimal.valueOf(25_000)),
                        Set.of(compatibleCarVersion)
                )
        );
    }
}
