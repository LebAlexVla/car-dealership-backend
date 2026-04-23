package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.user.User;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarConfigurationRepositoryIT extends AbstractRepositoryDatabaseIT {

    @Autowired
    private CarConfigurationDefaulterRepository carConfigurationDefaulterRepository;

    @Autowired
    private CarConfigurationCustomizerRepository carConfigurationCustomizerRepository;

    @Autowired
    private CarConfigurationRepository carConfigurationRepository;

    @Test
    void shouldFindAndDeleteDefaulterByCarVersionId() {
        CarVersion carVersion = createCarVersion("3.0 TFSI", true);
        Detail requiredDetail = createDetail("Matrix LED", "HEADLIGHT", carVersion);
        CarConfigurationDefaulter defaulter = carConfigurationDefaulterRepository.save(
                new CarConfigurationDefaulter(carVersion, Set.of(requiredDetail))
        );

        var found = carConfigurationDefaulterRepository.findByCarVersionId(carVersion.getId());
        carConfigurationDefaulterRepository.deleteByCarVersionId(carVersion.getId());
        var deleted = carConfigurationDefaulterRepository.findByCarVersionId(carVersion.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(defaulter.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void shouldPersistCustomizerAndConfiguration() {
        User client = createUser("+79990000002");
        CarVersion carVersion = createCarVersion("45 TFSI", true);
        Detail requiredDetail = createDetail("Standard seats", "SEATS", carVersion);

        CarConfigurationCustomizer customizer = carConfigurationCustomizerRepository.save(
                new CarConfigurationCustomizer(client, carVersion, Set.of(requiredDetail))
        );
        CarConfiguration configuration = carConfigurationRepository.save(customizer.build());

        assertThat(carConfigurationCustomizerRepository.findById(customizer.getId())).isPresent();
        assertThat(carConfigurationRepository.findById(configuration.getId())).isPresent();
    }
}
