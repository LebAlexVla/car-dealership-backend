package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.vo.Price;
import ru.lebedev.dealership.domain.detail.Detail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarCatalogRepositoryIT extends AbstractRepositoryDatabaseIT {

    @Test
    void shouldPersistCarHead() {
        CarHead saved = carHeadRepository.save(new CarHead("Audi", "A6", BodyType.SEDAN));

        var found = carHeadRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getBrand()).isEqualTo("Audi");
    }

    @Test
    void shouldFindCarVersionByTestDriveAvailability() {
        CarVersion available = createCarVersion("2.0 TFSI", true);
        createCarVersion("2.5 TFSI", false);

        List<CarVersion> found = carVersionRepository.findByTestDriveAvailable(true);

        assertThat(found).extracting(CarVersion::getId).contains(available.getId());
    }

    @Test
    void shouldPersistDetailWithCompatibleCars() {
        CarVersion carVersion = createCarVersion("3.0 TDI", true);
        Detail detail = new Detail(
                "Winter Package",
                "PACKAGE",
                new Price(BigDecimal.valueOf(100_000)),
                Set.of(carVersion)
        );
        Detail saved = detailRepository.save(detail);

        var found = detailRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getCompatibleCars()).extracting(CarVersion::getId).contains(carVersion.getId());
    }
}
