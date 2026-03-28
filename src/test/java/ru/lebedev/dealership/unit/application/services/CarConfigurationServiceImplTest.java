package ru.lebedev.dealership.unit.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.*;
import ru.lebedev.dealership.presentation.carconfiguration.CarConfigurationDefaulterInputDto;
import ru.lebedev.dealership.application.contracts.carconfiguration.requests.*;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.DetailType;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class CarConfigurationServiceImplTest {

    private CarConfigurationDefaulterRepository defaulterRepository;
    private CarConfigurationCustomizerRepository customizerRepository;
    private CarConfigurationRepository configurationRepository;
    private DetailRepository detailRepository;

    private CarConfigurationServiceImpl service;

    @BeforeEach
    void setup() {
        defaulterRepository = mock(CarConfigurationDefaulterRepository.class);
        customizerRepository = mock(CarConfigurationCustomizerRepository.class);
        configurationRepository = mock(CarConfigurationRepository.class);
        detailRepository = mock(DetailRepository.class);


        service = new CarConfigurationServiceImpl(
                defaulterRepository,
                customizerRepository,
                configurationRepository,
                detailRepository
        );
    }

    @Test
    void addDefaulter_shouldCallRepository() {

        var request = new AddCarConfigurationDefaulterRequest(new CarConfigurationDefaulterInputDto(1L, new HashMap<>()));
        when(defaulterRepository.save(any())).thenReturn(new CarConfigurationDefaulter(0L, 0L, new HashMap<DetailType, Long>()));

        service.addDefaulter(request);

        verify(defaulterRepository).save(any());
    }

    @Test
    void deleteDefaulter_shouldCallRepository() {

        service.deleteDefaulter(new DeleteCarConfigurationDefaulterRequest(5L));

        verify(defaulterRepository).delete(5L);
    }

    @Test
    void addCustomizer_shouldCallRepositories() {

        var defaulter = mock(ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter.class);
        var customizer = mock(ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer.class);

        when(defaulterRepository.findById(1L)).thenReturn(defaulter);
        when(defaulter.create(anyLong(), anyLong())).thenReturn(customizer);
        when(customizerRepository.save(customizer)).thenReturn(customizer);

        service.addCustomizer(new AddCarConfigurationCustomizerRequest(10L,1L));

        verify(defaulterRepository).findById(1L);
        verify(customizerRepository).save(customizer);
    }

    @Test
    void deleteCustomizer_shouldCallRepository() {

        service.deleteCustomizer(new DeleteCarConfigurationCustomizerRequest(3L));

        verify(customizerRepository).delete(3L);
    }

    @Test
    void addCustomRequiredDetail_shouldCallRepositories() {

        var customizer = mock(ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer.class);
        var detail = mock(ru.lebedev.dealership.domain.detail.Detail.class);

        when(customizerRepository.findById(1L)).thenReturn(customizer);
        when(detailRepository.findById(2L)).thenReturn(detail);

        service.addCustomRequiredDetail(new AddCustomDetailRequest(1L,2L));

        verify(customizer).withRequiredDetail(detail);
    }

    @Test
    void addCustomOptionalDetail_shouldCallRepositories() {

        var customizer = mock(ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer.class);
        var detail = mock(ru.lebedev.dealership.domain.detail.Detail.class);

        when(customizerRepository.findById(1L)).thenReturn(customizer);
        when(detailRepository.findById(2L)).thenReturn(detail);

        service.addCustomOptionalDetail(new AddCustomDetailRequest(1L,2L));

        verify(customizer).withOptionalDetail(detail);
    }

    @Test
    void createCarConfiguration_shouldCallRepositories() {

        var customizer = mock(ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer.class);
        var configuration = mock(ru.lebedev.dealership.domain.carconfiguration.CarConfiguration.class);

        when(customizerRepository.findById(1L)).thenReturn(customizer);
        when(customizer.build(anyLong())).thenReturn(configuration);
        when(configurationRepository.save(configuration)).thenReturn(configuration);

        service.createCarConfiguration(new CreateCarConfigurationRequest(1L));

        verify(customizerRepository).findById(1L);
        verify(configurationRepository).save(configuration);
    }
}