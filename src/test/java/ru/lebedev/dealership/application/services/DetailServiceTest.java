package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.DetailRepository;
import ru.lebedev.dealership.support.TestDataFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetailServiceTest {

    @Mock
    private DetailRepository detailRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Test
    void shouldCreateDetailWithCompatibleCarsAndHandleCrudMethods() {
        DetailService service = new DetailService(detailRepository, carVersionRepository);
        var carVersion = TestDataFactory.carVersion(10L);
        var detail = TestDataFactory.detail(1L, "Bose", "audio", null);

        when(carVersionRepository.findAllById(Set.of(10L))).thenReturn(List.of(carVersion));
        when(detailRepository.save(detail)).thenReturn(detail);
        when(detailRepository.findById(1L)).thenReturn(Optional.of(detail));
        when(detailRepository.findAll(any(Specification.class))).thenReturn(List.of(detail));

        assertEquals(1L, service.create(detail, Set.of(10L)));
        assertEquals(1L, service.create(detail, null));
        assertEquals(Optional.of(detail), service.findById(1L));
        assertEquals(1, service.find(new DetailFilter(null, null, null, null)).size());

        service.delete(1L);
        verify(detailRepository).deleteById(1L);
    }
}
