package ru.lebedev.dealership.integration.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarHeadRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarHeadRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private CarHeadRepository carHeadRepository;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void shouldSaveCarHead() {
        CarHead carHead = createCarHead();

        CarHead saved = carHeadRepository.save(carHead);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void shouldFindById() {
        CarHead saved = carHeadRepository.save(createCarHead());

        CarHead found = carHeadRepository.findById(saved.getId())
                .orElseThrow();

        assertThat(found.getId()).isEqualTo(saved.getId());
    }

    @Test
    void shouldFindAll() {
        carHeadRepository.save(createCarHead());
        carHeadRepository.save(createCarHead());

        Iterable<CarHead> all = carHeadRepository.findAll();

        assertThat(all).hasSize(2);
    }

    private CarHead createCarHead() {
        return new CarHead(
                "Toyota",
                "Camry",
                BodyType.SEDAN
        );
    }
}