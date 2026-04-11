package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.lebedev.dealership.application.exceptions.*;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.infrastructure.persistence.repository.*;
import ru.lebedev.dealership.support.TestDataFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarConfigurationServiceTest {

    @Mock private CarConfigurationDefaulterRepository defaulterRepository;
    @Mock private CarConfigurationCustomizerRepository customizerRepository;
    @Mock private CarConfigurationRepository configurationRepository;
    @Mock private UserRepository userRepository;
    @Mock private CarVersionRepository carVersionRepository;
    @Mock private DetailRepository detailRepository;

    @Test
    void shouldCreateAndFindAllConfigurationPartsAndDelete() {
        CarConfigurationService service = new CarConfigurationService(
                defaulterRepository,
                customizerRepository,
                configurationRepository,
                userRepository,
                carVersionRepository,
                detailRepository
        );

        var car = TestDataFactory.carVersion(1L);
        var detail = TestDataFactory.detail(2L, "Basic Audio", "audio", car);
        var requiredReplacement = TestDataFactory.detail(5L, "Premium Audio", "audio", car);
        var optionalDetail = TestDataFactory.detail(6L, "Sunroof", "roof", car);
        var defaultAdditionalDetail = TestDataFactory.detail(4L, "Matrix", "lights", car);
        var user = TestDataFactory.user(3L);
        var defaulter = new CarConfigurationDefaulter(car, new HashSet<>(Set.of(detail)));
        TestDataFactory.setId(defaulter, 10L);
        var customizer = defaulter.create(user);
        TestDataFactory.setId(customizer, 20L);
        CarConfiguration configuration = customizer.build();
        TestDataFactory.setId(configuration, 30L);

        when(carVersionRepository.findById(1L)).thenReturn(Optional.of(car));
        when(detailRepository.findAllById(Set.of(2L))).thenReturn(List.of(detail));
        when(defaulterRepository.save(any(CarConfigurationDefaulter.class))).thenReturn(defaulter);

        when(defaulterRepository.findById(10L)).thenReturn(Optional.of(defaulter));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        when(customizerRepository.save(any(CarConfigurationCustomizer.class))).thenReturn(customizer);

        when(customizerRepository.findById(20L)).thenReturn(Optional.of(customizer));
        when(configurationRepository.save(any(CarConfiguration.class))).thenReturn(configuration);

        when(defaulterRepository.findByCarVersionId(1L)).thenReturn(Optional.of(defaulter));
        when(configurationRepository.findById(30L)).thenReturn(Optional.of(configuration));

        when(defaulterRepository.findAll()).thenReturn(List.of(defaulter));
        when(customizerRepository.findAll()).thenReturn(List.of(customizer));
        when(configurationRepository.findAll()).thenReturn(List.of(configuration));
        when(detailRepository.findById(2L)).thenReturn(Optional.of(detail));
        when(detailRepository.findById(4L)).thenReturn(Optional.of(defaultAdditionalDetail));
        when(detailRepository.findById(5L)).thenReturn(Optional.of(requiredReplacement));
        when(detailRepository.findById(6L)).thenReturn(Optional.of(optionalDetail));

        assertEquals(10L, service.createDefaulter(1L, Set.of(2L)));
        assertEquals(20L, service.createCustomizer(10L, 3L));
        assertEquals(30L, service.createCarConfiguration(20L));

        assertTrue(service.findDefaulterByCarVersionId(1L).isPresent());
        assertTrue(service.findCustomizerById(20L).isPresent());
        assertTrue(service.findConfigurationById(30L).isPresent());

        assertEquals(1, service.findAllDefaulters().size());
        assertEquals(1, service.findAllCustomizers().size());
        assertEquals(1, service.findAllConfigurations().size());

        service.addDefaultDetail(10L, 4L);
        service.selectCustomRequiredDetail(20L, 5L);
        service.selectCustomOptionalDetail(20L, 6L);
        service.rejectCustomOptionalDetail(20L, 6L);

        service.deleteDefaulterByCarId(1L);
        service.deleteCustomizerById(20L);
        service.deleteConfigurationById(30L);

        verify(defaulterRepository).deleteByCarVersionId(1L);
        verify(customizerRepository).deleteById(20L);
        verify(configurationRepository).deleteById(30L);
    }

    @Test
    void shouldThrowWhenDependenciesMissing() {
        CarConfigurationService service = new CarConfigurationService(
                defaulterRepository,
                customizerRepository,
                configurationRepository,
                userRepository,
                carVersionRepository,
                detailRepository
        );

        when(carVersionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarVersionNotFoundException.class, () -> service.createDefaulter(1L, Set.of()));

        when(defaulterRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(DefaulterNotFoundException.class, () -> service.createCustomizer(10L, 3L));
        assertThrows(DefaulterNotFoundException.class, () -> service.addDefaultDetail(10L, 2L));

        when(customizerRepository.findById(20L)).thenReturn(Optional.empty());
        assertThrows(CustomizerNotFoundException.class, () -> service.createCarConfiguration(20L));
        assertThrows(CustomizerNotFoundException.class, () -> service.selectCustomRequiredDetail(20L, 2L));
        assertThrows(CustomizerNotFoundException.class, () -> service.selectCustomOptionalDetail(20L, 2L));
        assertThrows(CustomizerNotFoundException.class, () -> service.rejectCustomOptionalDetail(20L, 2L));

        when(defaulterRepository.findById(10L)).thenReturn(Optional.of(new CarConfigurationDefaulter(TestDataFactory.carVersion(1L), new HashSet<>())));
        when(detailRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(DetailNotFoundException.class, () -> service.addDefaultDetail(10L, 2L));

        when(defaulterRepository.findById(10L)).thenReturn(Optional.of(new CarConfigurationDefaulter(TestDataFactory.carVersion(1L), new HashSet<>())));
        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.createCustomizer(10L, 3L));
    }
}
