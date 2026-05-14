package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.application.exceptions.CarVersionNotFoundException;
import ru.lebedev.dealership.application.exceptions.CustomizerNotFoundException;
import ru.lebedev.dealership.application.exceptions.DefaulterNotFoundException;
import ru.lebedev.dealership.application.exceptions.DetailNotFoundException;
import ru.lebedev.dealership.application.exceptions.UserNotFoundException;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.carconfiguration.CarConfiguration;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationCustomizer;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationCustomizerRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationDefaulterRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarConfigurationRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.DetailRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.lebedev.dealership.TestDataFactory.carConfiguration;
import static ru.lebedev.dealership.TestDataFactory.carConfigurationCustomizer;
import static ru.lebedev.dealership.TestDataFactory.carConfigurationDefaulter;
import static ru.lebedev.dealership.TestDataFactory.carVersion;
import static ru.lebedev.dealership.TestDataFactory.compatibleDetail;
import static ru.lebedev.dealership.TestDataFactory.customer;
import static ru.lebedev.dealership.TestDataFactory.price;
import static ru.lebedev.dealership.TestDataFactory.withId;

@ExtendWith(MockitoExtension.class)
class CarConfigurationServiceTest {

    @Mock
    private CarConfigurationDefaulterRepository defaulterRepository;

    @Mock
    private CarConfigurationCustomizerRepository customizerRepository;

    @Mock
    private CarConfigurationRepository configurationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Mock
    private DetailRepository detailRepository;

    private CarConfigurationService carConfigurationService;

    @BeforeEach
    void setUp() {
        carConfigurationService = new CarConfigurationService(
                defaulterRepository,
                customizerRepository,
                configurationRepository,
                userRepository,
                carVersionRepository,
                detailRepository
        );
    }

    @Test
    void createDefaulter_shouldCreateDefaulter() {
        CarVersion carVersion = carVersion(10L);
        Detail detail = compatibleDetail(20L, carVersion);
        CarConfigurationDefaulter savedDefaulter =
                carConfigurationDefaulter(30L, carVersion, Set.of(detail));

        when(carVersionRepository.findById(10L)).thenReturn(Optional.of(carVersion));
        when(detailRepository.findAllById(Set.of(20L))).thenReturn(List.of(detail));
        when(defaulterRepository.save(any(CarConfigurationDefaulter.class)))
                .thenReturn(savedDefaulter);

        Long result = carConfigurationService.createDefaulter(10L, Set.of(20L));

        assertEquals(30L, result);
        verify(carVersionRepository).findById(10L);
        verify(detailRepository).findAllById(Set.of(20L));
        verify(defaulterRepository).save(any(CarConfigurationDefaulter.class));
    }

    @Test
    void createDefaulter_shouldThrowWhenCarVersionNotFound() {
        when(carVersionRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(
                CarVersionNotFoundException.class,
                () -> carConfigurationService.createDefaulter(10L, Set.of(20L))
        );

        verify(defaulterRepository, never()).save(any());
    }

    @Test
    void createCustomizer_shouldCreateCustomizer() {
        User user = customer(1L);
        CarVersion carVersion = carVersion(10L);
        Detail detail = compatibleDetail(20L, carVersion);

        CarConfigurationDefaulter defaulter =
                carConfigurationDefaulter(30L, carVersion, Set.of(detail));
        CarConfigurationCustomizer savedCustomizer =
                carConfigurationCustomizer(40L, user, carVersion, Set.of(detail));

        when(defaulterRepository.findById(30L)).thenReturn(Optional.of(defaulter));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(customizerRepository.save(any(CarConfigurationCustomizer.class)))
                .thenReturn(savedCustomizer);

        Long result = carConfigurationService.createCustomizer(30L, 1L);

        assertEquals(40L, result);
        verify(defaulterRepository).findById(30L);
        verify(userRepository).findById(1L);
        verify(customizerRepository).save(any(CarConfigurationCustomizer.class));
    }

    @Test
    void createCustomizer_shouldThrowWhenDefaulterNotFound() {
        when(defaulterRepository.findById(30L)).thenReturn(Optional.empty());

        assertThrows(
                DefaulterNotFoundException.class,
                () -> carConfigurationService.createCustomizer(30L, 1L)
        );

        verify(customizerRepository, never()).save(any());
    }

    @Test
    void createCustomizer_shouldThrowWhenUserNotFound() {
        CarConfigurationDefaulter defaulter = carConfigurationDefaulter(30L);

        when(defaulterRepository.findById(30L)).thenReturn(Optional.of(defaulter));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> carConfigurationService.createCustomizer(30L, 1L)
        );

        verify(customizerRepository, never()).save(any());
    }

    @Test
    void createCarConfiguration_shouldCreateConfiguration() {
        User user = customer(1L);
        CarVersion carVersion = carVersion(10L);
        Detail detail = compatibleDetail(20L, carVersion);

        CarConfigurationCustomizer customizer =
                carConfigurationCustomizer(30L, user, carVersion, Set.of(detail));
        CarConfiguration savedConfiguration =
                carConfiguration(40L, user, carVersion, Set.of(detail));

        when(customizerRepository.findById(30L)).thenReturn(Optional.of(customizer));
        when(configurationRepository.save(any(CarConfiguration.class))).thenReturn(savedConfiguration);

        Long result = carConfigurationService.createCarConfiguration(30L);

        assertEquals(40L, result);
        verify(customizerRepository).findById(30L);
        verify(configurationRepository).save(any(CarConfiguration.class));
    }

    @Test
    void createCarConfiguration_shouldThrowWhenCustomizerNotFound() {
        when(customizerRepository.findById(30L)).thenReturn(Optional.empty());

        assertThrows(
                CustomizerNotFoundException.class,
                () -> carConfigurationService.createCarConfiguration(30L)
        );

        verify(configurationRepository, never()).save(any());
    }

    @Test
    void findDefaulterByCarVersionId_shouldReturnDefaulter() {
        CarConfigurationDefaulter defaulter = carConfigurationDefaulter(1L);

        when(defaulterRepository.findByCarVersionId(10L)).thenReturn(Optional.of(defaulter));

        Optional<CarConfigurationDefaulter> result =
                carConfigurationService.findDefaulterByCarVersionId(10L);

        assertEquals(Optional.of(defaulter), result);
    }

    @Test
    void findCustomizerById_shouldReturnCustomizer() {
        CarConfigurationCustomizer customizer = carConfigurationCustomizer(1L);

        when(customizerRepository.findById(1L)).thenReturn(Optional.of(customizer));

        Optional<CarConfigurationCustomizer> result =
                carConfigurationService.findCustomizerById(1L);

        assertEquals(Optional.of(customizer), result);
    }

    @Test
    void findConfigurationById_shouldReturnConfiguration() {
        CarConfiguration configuration = carConfiguration(1L);

        when(configurationRepository.findById(1L)).thenReturn(Optional.of(configuration));

        Optional<CarConfiguration> result =
                carConfigurationService.findConfigurationById(1L);

        assertEquals(Optional.of(configuration), result);
    }

    @Test
    void findAllDefaulters_shouldReturnAllDefaulters() {
        CarConfigurationDefaulter defaulter = carConfigurationDefaulter(1L);

        when(defaulterRepository.findAll()).thenReturn(List.of(defaulter));

        List<CarConfigurationDefaulter> result = carConfigurationService.findAllDefaulters();

        assertEquals(List.of(defaulter), result);
    }

    @Test
    void findAllCustomizers_shouldReturnAllCustomizers() {
        CarConfigurationCustomizer customizer = carConfigurationCustomizer(1L);

        when(customizerRepository.findAll()).thenReturn(List.of(customizer));

        List<CarConfigurationCustomizer> result = carConfigurationService.findAllCustomizers();

        assertEquals(List.of(customizer), result);
    }

    @Test
    void findAllConfigurations_shouldReturnAllConfigurations() {
        CarConfiguration configuration = carConfiguration(1L);

        when(configurationRepository.findAll()).thenReturn(List.of(configuration));

        List<CarConfiguration> result = carConfigurationService.findAllConfigurations();

        assertEquals(List.of(configuration), result);
    }

    @Test
    void addDefaultDetail_shouldAddRequiredDetail() {
        CarVersion carVersion = carVersion(10L);

        Detail existingDetail = new Detail(
                "Base Engine",
                "ENGINE",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        withId(existingDetail, 20L);

        Detail newDetail = new Detail(
                "Premium Salon",
                "SALON",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        withId(newDetail, 21L);

        CarConfigurationDefaulter defaulter =
                carConfigurationDefaulter(30L, carVersion, Set.of(existingDetail));

        when(defaulterRepository.findById(30L)).thenReturn(Optional.of(defaulter));
        when(detailRepository.findById(21L)).thenReturn(Optional.of(newDetail));

        carConfigurationService.addDefaultDetail(30L, 21L);

        assertTrue(defaulter.getRequiredDetails().contains(newDetail));
        verify(defaulterRepository).findById(30L);
        verify(detailRepository).findById(21L);
    }

    @Test
    void addDefaultDetail_shouldThrowWhenDetailTypeAlreadyExists() {
        CarVersion carVersion = carVersion(10L);

        Detail existingDetail = new Detail(
                "Base Package",
                "PACKAGE",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        withId(existingDetail, 20L);

        Detail duplicateDetail = new Detail(
                "Premium Package",
                "PACKAGE",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        withId(duplicateDetail, 21L);

        CarConfigurationDefaulter defaulter =
                carConfigurationDefaulter(30L, carVersion, Set.of(existingDetail));

        when(defaulterRepository.findById(30L)).thenReturn(Optional.of(defaulter));
        when(detailRepository.findById(21L)).thenReturn(Optional.of(duplicateDetail));

        assertThrows(
                ru.lebedev.dealership.domain.exceptions.DuplicateDefaultDetailTypeException.class,
                () -> carConfigurationService.addDefaultDetail(30L, 21L)
        );

        verify(defaulterRepository).findById(30L);
        verify(detailRepository).findById(21L);
    }

    @Test
    void addDefaultDetail_shouldThrowWhenDefaulterNotFound() {
        when(defaulterRepository.findById(30L)).thenReturn(Optional.empty());

        assertThrows(
                DefaulterNotFoundException.class,
                () -> carConfigurationService.addDefaultDetail(30L, 21L)
        );
    }

    @Test
    void addDefaultDetail_shouldThrowWhenDetailNotFound() {
        CarConfigurationDefaulter defaulter = carConfigurationDefaulter(30L);

        when(defaulterRepository.findById(30L)).thenReturn(Optional.of(defaulter));
        when(detailRepository.findById(21L)).thenReturn(Optional.empty());

        assertThrows(
                DetailNotFoundException.class,
                () -> carConfigurationService.addDefaultDetail(30L, 21L)
        );
    }

    @Test
    void selectCustomRequiredDetail_shouldReplaceRequiredDetail() {
        User user = customer(1L);
        CarVersion carVersion = carVersion(10L);
        Detail oldRequired = compatibleDetail(20L, carVersion);
        Detail newRequired = compatibleDetail(21L, carVersion);

        CarConfigurationCustomizer customizer =
                carConfigurationCustomizer(30L, user, carVersion, Set.of(oldRequired));

        when(customizerRepository.findById(30L)).thenReturn(Optional.of(customizer));
        when(detailRepository.findById(21L)).thenReturn(Optional.of(newRequired));

        carConfigurationService.selectCustomRequiredDetail(30L, 21L);

        assertTrue(customizer.getRequiredDetails().contains(newRequired));
        assertFalse(customizer.getRequiredDetails().contains(oldRequired));
    }

    @Test
    void selectCustomRequiredDetail_shouldThrowWhenCustomizerNotFound() {
        when(customizerRepository.findById(30L)).thenReturn(Optional.empty());

        assertThrows(
                CustomizerNotFoundException.class,
                () -> carConfigurationService.selectCustomRequiredDetail(30L, 21L)
        );
    }

    @Test
    void selectCustomOptionalDetail_shouldAddOptionalDetail() {
        User user = customer(1L);
        CarVersion carVersion = carVersion(10L);

        Detail required = new Detail(
                "Base Engine",
                "ENGINE",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        Detail optional = new Detail(
                "Premium Salon",
                "SALON",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        withId(required, 20L);
        withId(optional, 21L);

        CarConfigurationCustomizer customizer =
                carConfigurationCustomizer(30L, user, carVersion, Set.of(required));

        when(customizerRepository.findById(30L)).thenReturn(Optional.of(customizer));
        when(detailRepository.findById(21L)).thenReturn(Optional.of(optional));

        carConfigurationService.selectCustomOptionalDetail(30L, 21L);

        assertTrue(customizer.getOptionalDetails().contains(optional));
    }

    @Test
    void rejectCustomOptionalDetail_shouldRemoveOptionalDetail() {
        User user = customer(1L);
        CarVersion carVersion = carVersion(10L);

        Detail required = new Detail(
                "Base Engine",
                "ENGINE",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        Detail optional = new Detail(
                "Premium Salon",
                "SALON",
                price(),
                new HashSet<>(Set.of(carVersion))
        );
        withId(required, 20L);
        withId(optional, 21L);

        CarConfigurationCustomizer customizer =
                carConfigurationCustomizer(30L, user, carVersion, Set.of(required));
        customizer.selectOptionalDetail(optional);

        when(customizerRepository.findById(30L)).thenReturn(Optional.of(customizer));
        when(detailRepository.findById(21L)).thenReturn(Optional.of(optional));

        carConfigurationService.rejectCustomOptionalDetail(30L, 21L);

        assertFalse(customizer.getOptionalDetails().contains(optional));
    }

    @Test
    void deleteDefaulterByCarId_shouldDeleteByCarVersionId() {
        carConfigurationService.deleteDefaulterByCarId(10L);

        verify(defaulterRepository).deleteByCarVersionId(10L);
    }

    @Test
    void deleteCustomizerById_shouldDeleteCustomizer() {
        carConfigurationService.deleteCustomizerById(30L);

        verify(customizerRepository).deleteById(30L);
    }

    @Test
    void deleteConfigurationById_shouldDeleteConfiguration() {
        carConfigurationService.deleteConfigurationById(40L);

        verify(configurationRepository).deleteById(40L);
    }
}