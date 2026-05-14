package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.application.exceptions.CarVersionNotAvailableForTestDrive;
import ru.lebedev.dealership.application.exceptions.CarVersionNotFoundException;
import ru.lebedev.dealership.application.exceptions.UserNotFoundException;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestDriveServiceTest {

    @Mock
    private TestDriveRepository testDriveRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Mock
    private CurrentUserService currentUserService;

    private TestDriveService testDriveService;

    @BeforeEach
    void setUp() {
        testDriveService = new TestDriveService(
                testDriveRepository,
                userRepository,
                carVersionRepository,
                currentUserService
        );
    }

    @Test
    void create_shouldCreateTestDrive() {
        User user = mock(User.class);
        CarVersion carVersion = mock(CarVersion.class);
        TestDrive savedTestDrive = mock(TestDrive.class);
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 1, 12, 0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.of(carVersion));
        when(carVersion.isTestDriveAvailable()).thenReturn(true);
        when(testDriveRepository.save(any(TestDrive.class))).thenReturn(savedTestDrive);
        when(savedTestDrive.getId()).thenReturn(100L);

        Long result = testDriveService.create(1L, 2L, dateTime);

        assertEquals(100L, result);
        verify(testDriveRepository).save(any(TestDrive.class));
    }

    @Test
    void create_shouldThrowWhenUserNotFound() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 1, 12, 0);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> testDriveService.create(1L, 2L, dateTime));
        verify(testDriveRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowWhenCarVersionNotFound() {
        User user = mock(User.class);
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 1, 12, 0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CarVersionNotFoundException.class, () -> testDriveService.create(1L, 2L, dateTime));
        verify(testDriveRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowWhenCarVersionNotAvailable() {
        User user = mock(User.class);
        CarVersion carVersion = mock(CarVersion.class);
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 1, 12, 0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.of(carVersion));
        when(carVersion.isTestDriveAvailable()).thenReturn(false);
        when(carVersion.getId()).thenReturn(2L);

        assertThrows(CarVersionNotAvailableForTestDrive.class, () -> testDriveService.create(1L, 2L, dateTime));
        verify(testDriveRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnTestDrive() {
        TestDrive testDrive = mock(TestDrive.class);

        when(testDriveRepository.findById(1L)).thenReturn(Optional.of(testDrive));

        Optional<TestDrive> result = testDriveService.findById(1L);

        assertEquals(Optional.of(testDrive), result);
    }

    @Test
    void findByClientId_shouldReturnTestDrivesForManager() {
        TestDrive testDrive = mock(TestDrive.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(true);
        when(testDriveRepository.findByClientId(1L)).thenReturn(List.of(testDrive));

        List<TestDrive> result = testDriveService.findByClientId(1L);

        assertEquals(List.of(testDrive), result);
    }

    @Test
    void findByClientId_shouldReturnEmptyListForForeignClientWhenUser() {
        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(10L);

        List<TestDrive> result = testDriveService.findByClientId(99L);

        assertTrue(result.isEmpty());
        verify(testDriveRepository, never()).findByClientId(anyLong());
    }

    @Test
    void findAll_shouldReturnAllTestDrivesForAdmin() {
        TestDrive testDrive = mock(TestDrive.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(true);
        when(testDriveRepository.findAll()).thenReturn(List.of(testDrive));

        List<TestDrive> result = testDriveService.findAll();

        assertEquals(List.of(testDrive), result);
        verify(testDriveRepository).findAll();
    }

    @Test
    void findAll_shouldReturnOnlyCurrentUserTestDrivesForUser() {
        TestDrive testDrive = mock(TestDrive.class);

        when(currentUserService.hasRole("ADMIN")).thenReturn(false);
        when(currentUserService.hasRole("MANAGER")).thenReturn(false);
        when(currentUserService.getCurrentUserId()).thenReturn(10L);
        when(testDriveRepository.findByClientId(10L)).thenReturn(List.of(testDrive));

        List<TestDrive> result = testDriveService.findAll();

        assertEquals(List.of(testDrive), result);
        verify(testDriveRepository).findByClientId(10L);
        verify(testDriveRepository, never()).findAll();
    }

    @Test
    void findAllCars_shouldReturnAvailableCarIds() {
        CarVersion first = mock(CarVersion.class);
        CarVersion second = mock(CarVersion.class);

        when(first.getId()).thenReturn(1L);
        when(second.getId()).thenReturn(2L);
        when(carVersionRepository.findByTestDriveAvailable(true)).thenReturn(List.of(first, second));

        List<Long> result = testDriveService.findAllCars();

        assertEquals(List.of(1L, 2L), result);
    }

    @Test
    void addCar_shouldMarkCarVersionAvailable() {
        CarVersion carVersion = mock(CarVersion.class);

        when(carVersionRepository.findById(1L)).thenReturn(Optional.of(carVersion));

        testDriveService.addCar(1L);

        verify(carVersion).setTestDriveAvailable(true);
    }

    @Test
    void removeCar_shouldMarkCarVersionUnavailable() {
        CarVersion carVersion = mock(CarVersion.class);

        when(carVersionRepository.findById(1L)).thenReturn(Optional.of(carVersion));

        testDriveService.removeCar(1L);

        verify(carVersion).setTestDriveAvailable(false);
    }

    @Test
    void deleteById_shouldDeleteTestDrive() {
        testDriveService.deleteById(1L);

        verify(testDriveRepository).deleteById(1L);
    }
}