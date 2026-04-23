package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.order.configuredcar.ConfiguredCarOrder;
import ru.lebedev.dealership.domain.order.stock.StockOrder;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.user.User;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderAndDriveRepositoryIT extends AbstractRepositoryDatabaseIT {

    @Autowired
    private TestDriveRepository testDriveRepository;

    @Autowired
    private StockOrderRepository stockOrderRepository;

    @Autowired
    private ConfiguredCarOrderRepository configuredCarOrderRepository;

    @Autowired
    private CarConfigurationRepository carConfigurationRepository;

    @Test
    void shouldFindTestDriveByClientId() {
        User client = createUser("+79990000003");
        CarVersion carVersion = createCarVersion("40 TDI", true);
        TestDrive saved = testDriveRepository.save(new TestDrive(client, carVersion, LocalDateTime.now().plusDays(1)));

        var found = testDriveRepository.findByClientId(client.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    void shouldFindStockOrderByClientId() {
        User client = createUser("+79990000004");
        CarVersion carVersion = createCarVersion("35 TFSI", true);
        StockOrder saved = stockOrderRepository.save(new StockOrder(client, carVersion));

        var found = stockOrderRepository.findByClientId(client.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    void shouldFindConfiguredCarOrderByConfigurationClientId() {
        User client = createUser("+79990000005");
        CarVersion carVersion = createCarVersion("55 TFSI", true);
        Detail detail = createDetail("Panorama roof", "ROOF", carVersion);
        CarConfiguration configuration = carConfigurationRepository.save(
                new CarConfiguration(client, carVersion, Set.of(detail))
        );
        ConfiguredCarOrder saved = configuredCarOrderRepository.save(new ConfiguredCarOrder(configuration));

        var found = configuredCarOrderRepository.findByConfigurationClientId(client.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }
}
