package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.contracts.detail.models.DetailFilterDto;
import ru.lebedev.dealership.application.contracts.detail.models.DetailInputDto;
import ru.lebedev.dealership.application.contracts.detail.requests.*;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailType;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

class DetailServiceImplTest {

    private DetailRepository repository;
    private DetailServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(DetailRepository.class);
        service = new DetailServiceImpl(repository);
    }

    @Test
    void addDetail_shouldSave() {

        DetailInputDto dto =
                new DetailInputDto("wheel","type", new BigDecimal(1),null);

        AddDetailRequest request =
                new AddDetailRequest(dto);

        Detail saved = mock(Detail.class);

        when(repository.save(any())).thenReturn(saved);

        service.addDetail(request);

        verify(repository).save(any());
    }

    @Test
    void deleteDetail_shouldDelete() {

        service.deleteDetail(new DeleteDetailRequest(5L));

        verify(repository).delete(5L);
    }

    @Test
    void showSpecificDetail_shouldFind() {
        Detail detail = new Detail(
                0L,
                "detail",
                new DetailType("type"),
                new Price(BigDecimal.ZERO),
                new HashSet<>()
        );

        when(repository.findById(5L)).thenReturn(detail);

        service.showSpecificDetail(new ShowSpecificDetailRequest(5L));

        verify(repository).findById(5L);
    }

    @Test
    void showDetails_shouldFilter() {

        when(repository.findByFilter(any()))
                .thenReturn(List.of());

        service.showDetails(new ShowDetailsRequest(new DetailFilterDto("type", new BigDecimal(1), new HashSet<Long>())));

        verify(repository).findByFilter(any());
    }
}