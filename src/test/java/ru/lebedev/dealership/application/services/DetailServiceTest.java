package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.vo.Price;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.DetailRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static ru.lebedev.dealership.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class DetailServiceTest {

    @Mock
    private DetailRepository detailRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    private DetailService detailService;

    @BeforeEach
    void setUp() {
        detailService = new DetailService(detailRepository, carVersionRepository);
    }

    @Test
    void create_shouldCreateDetailWithoutCompatibleCars() {
        Detail detail = new Detail(
                "Winter Package",
                "PACKAGE",
                new Price(BigDecimal.valueOf(100_000)),
                new HashSet<>()
        );
        Detail savedDetail = withId(detail, 1L);

        when(detailRepository.save(detail)).thenReturn(savedDetail);

        Long result = detailService.create(detail, null);

        assertEquals(1L, result);
        verify(carVersionRepository, never()).findAllById(any());
        verify(detailRepository).save(detail);
    }

    @Test
    void create_shouldCreateDetailWithCompatibleCars() {
        CarVersion firstCarVersion = carVersion(10L);
        CarVersion secondCarVersion = carVersion(11L);

        Detail detail = new Detail(
                "Winter Package",
                "PACKAGE",
                new Price(BigDecimal.valueOf(100_000)),
                new HashSet<>()
        );
        Detail savedDetail = withId(detail, 1L);

        when(carVersionRepository.findAllById(Set.of(10L, 11L)))
                .thenReturn(List.of(firstCarVersion, secondCarVersion));
        when(detailRepository.save(detail)).thenReturn(savedDetail);

        Long result = detailService.create(detail, Set.of(10L, 11L));

        assertEquals(1L, result);
        assertTrue(detail.getCompatibleCars().contains(firstCarVersion));
        assertTrue(detail.getCompatibleCars().contains(secondCarVersion));
        verify(carVersionRepository).findAllById(Set.of(10L, 11L));
        verify(detailRepository).save(detail);
    }

    @Test
    void create_shouldCallFindAllByIdWhenCompatibleCarsSetIsEmpty() {
        Detail detail = new Detail(
                "Winter Package",
                "PACKAGE",
                new Price(BigDecimal.valueOf(100_000)),
                new HashSet<>()
        );
        Detail savedDetail = withId(detail, 1L);

        when(carVersionRepository.findAllById(Set.of())).thenReturn(List.of());
        when(detailRepository.save(detail)).thenReturn(savedDetail);

        Long result = detailService.create(detail, Set.of());

        assertEquals(1L, result);
        verify(carVersionRepository).findAllById(Set.of());
        verify(detailRepository).save(detail);
    }

    @Test
    void findById_shouldReturnDetail() {
        Detail detail = detail(1L);

        when(detailRepository.findById(1L)).thenReturn(Optional.of(detail));

        Optional<Detail> result = detailService.findById(1L);

        assertEquals(Optional.of(detail), result);
    }

    @Test
    void findById_shouldReturnEmptyWhenDetailNotFound() {
        when(detailRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Detail> result = detailService.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void find_shouldReturnFilteredDetails() {
        Detail detail = detail(1L);
        DetailFilter filter = new DetailFilter(
                List.of("PACKAGE"),
                new Price(BigDecimal.valueOf(10_000)),
                new Price(BigDecimal.valueOf(200_000)),
                Set.of(10L)
        );

        when(detailRepository.findAll(org.mockito.ArgumentMatchers.<Specification<Detail>>any()))
                .thenReturn(List.of(detail));

        List<Detail> result = detailService.find(filter);

        assertEquals(List.of(detail), result);
        verify(detailRepository).findAll(org.mockito.ArgumentMatchers.<Specification<Detail>>any());
    }

    @Test
    void delete_shouldDeleteDetailById() {
        detailService.delete(1L);

        verify(detailRepository).deleteById(1L);
    }
}