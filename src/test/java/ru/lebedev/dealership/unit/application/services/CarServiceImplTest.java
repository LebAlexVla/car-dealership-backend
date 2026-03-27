package ru.lebedev.dealership.unit.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadFilterDto;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadInputDto;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionInputDto;
import ru.lebedev.dealership.application.contracts.car.requests.*;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.enums.BodyType;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.*;
import ru.lebedev.dealership.domain.shared.vo.Brand;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    private CarHeadRepository carHeadRepository;
    private CarVersionRepository carVersionRepository;
    private CarServiceImpl service;

    @BeforeEach
    void setup() {
        carHeadRepository = mock(CarHeadRepository.class);
        carVersionRepository = mock(CarVersionRepository.class);
        service = new CarServiceImpl(carHeadRepository, carVersionRepository);
    }

    @Test
    void addCarHead_shouldSaveAndReturnId() {

        CarHeadInputDto inputDto = new CarHeadInputDto("Toyota", "Camry", "SEDAN");

        AddCarHeadRequest request = new AddCarHeadRequest(inputDto);

        CarHead saved = new CarHead(5L, new Brand("Toyota"), new CarModel("Camry"), BodyType.SEDAN);

        when(carHeadRepository.save(any())).thenReturn(saved);

        Long id = service.addCarHead(request);

        assertEquals(5L, id);
        verify(carHeadRepository).save(any());
    }

    @Test
    void addCarVersion_shouldSaveAndReturnId() {

        CarVersionInputDto dto = new CarVersionInputDto("2.0 AT", 1L, "DIESEL", 150L, 2.0, "AUTOMATIC", "ALL_WHEEL", List.of("Black"), new BigDecimal(1));

        AddCarVersionRequest request = new AddCarVersionRequest(dto);

        CarVersion saved = mock(CarVersion.class);
        when(saved.carVersionId()).thenReturn(7L);

        when(carVersionRepository.save(any())).thenReturn(saved);

        Long id = service.addCarVersion(request);

        assertEquals(7L, id);
        verify(carVersionRepository).save(any());
    }

    @Test
    void deleteCarHead_shouldCallRepository() {

        service.deleteCarHead(new DeleteCarHeadRequest(10L));

        verify(carHeadRepository).delete(10L);
    }

    @Test
    void deleteCarVersion_shouldCallRepository() {

        service.deleteCarVersion(new DeleteCarVersionRequest(12L));

        verify(carVersionRepository).delete(12L);
    }

    @Test
    void showSpecificCarHead_shouldReturnMappedDto() {

        CarHead carHead = new CarHead(5L, new Brand("Toyota"), new CarModel("Camry"), BodyType.SEDAN);

        when(carHeadRepository.findById(5L)).thenReturn(carHead);

        var result = service.showSpecificCarHead(new ShowSpecificCarHeadRequest(5L));

        assertEquals(5L, result.carHeadId());
        assertEquals("Toyota", result.brand());
        assertEquals("Camry", result.model());
    }

    @Test
    void showSpecificCarVersion_shouldReturnMappedDto() {

        CarVersion carVersion = new CarVersion(
                2L,
                "2.0 AT",
                1L,
                new Engine(
                        FuelType.DIESEL,
                        new EnginePower(250L),
                        new EngineCapacity(3.5)
                ),
                GearboxType.AUTOMATIC,
                CarDrive.ALL_WHEEL,
                List.of(new CarColor("Black")),
                new Price(BigDecimal.valueOf(2000000))
        );

        when(carVersionRepository.findById(2L)).thenReturn(carVersion);

        var result = service.showSpecificCarVersion(new ShowSpecificCarVersionRequest(2L));

        assertEquals(2L, result.carVersionId());
        assertEquals("2.0 AT", result.carVersionName());
        assertEquals(1L, result.carHeadId());
        assertEquals("DIESEL", result.fuelType());
        assertEquals(250L, result.power());
    }

    @Test
    void showCarsHeads_shouldReturnMappedList() {

        CarHead carHead = new CarHead(3L, new Brand("BMW"), new CarModel("X5"), BodyType.SUV);

        when(carHeadRepository.findByFilter(any())).thenReturn(List.of(carHead));

        ShowCarsHeadsRequest request = new ShowCarsHeadsRequest(new CarHeadFilterDto("BMW", "X5", "SUV"));

        var result = service.showCarsHeads(request);

        assertEquals(1, result.size());
        assertEquals("BMW", result.getFirst().brand());
    }
}