package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import ru.lebedev.dealership.TestDataFactory;
import ru.lebedev.dealership.application.exceptions.CarHeadNotFoundException;
import ru.lebedev.dealership.application.filters.CarHeadFilter;
import ru.lebedev.dealership.application.filters.CarVersionFilter;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarHeadRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarHeadRepository carHeadRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Test
    void shouldCreateHeadVersionFindAndDelete() {
        CarService service = new CarService(carHeadRepository, carVersionRepository);
        CarHead carHead = TestDataFactory.carHead(1L);
        CarVersion carVersion = TestDataFactory.carVersion(2L);

        when(carHeadRepository.save(carHead)).thenReturn(carHead);
        when(carHeadRepository.findById(1L)).thenReturn(Optional.of(carHead));
        when(carVersionRepository.save(carVersion)).thenReturn(carVersion);
        when(carHeadRepository.findAll(any(Specification.class))).thenReturn(List.of(carHead));
        when(carVersionRepository.findAll(any(Specification.class))).thenReturn(List.of(carVersion));

        assertEquals(1L, service.createHead(carHead));
        assertEquals(2L, service.createVersion(carVersion, 1L));
        assertEquals(Optional.of(carHead), service.findHeadById(1L));
        assertEquals(Optional.empty(), service.findVersionById(9L));
        assertEquals(1, service.findHead(new CarHeadFilter(null, null, null)).size());
        assertEquals(1, service.findVersion(new CarVersionFilter(
                null, null, null, null, null, null, null, null, null, null
        )).size());

        service.deleteHead(1L);
        service.deleteVersion(2L);

        verify(carHeadRepository).deleteById(1L);
        verify(carVersionRepository).deleteById(2L);
    }

    @Test
    void shouldThrowWhenHeadNotFoundOnCreateVersion() {
        CarService service = new CarService(carHeadRepository, carVersionRepository);
        when(carHeadRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CarHeadNotFoundException.class, () -> service.createVersion(TestDataFactory.carVersion(2L), 1L));
    }
}
