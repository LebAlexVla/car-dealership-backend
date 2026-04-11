package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lebedev.dealership.application.exceptions.CarVersionNotAvailableForTestDrive;
import ru.lebedev.dealership.application.exceptions.CarVersionNotFoundException;
import ru.lebedev.dealership.application.exceptions.UserNotFoundException;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;
import ru.lebedev.dealership.support.TestDataFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestDriveServiceTest {

    @Mock
    private TestDriveRepository testDriveRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarVersionRepository carVersionRepository;

    @Test
    void shouldCreateAndManageTestDriveCars() {
        TestDriveService service = new TestDriveService(testDriveRepository, userRepository, carVersionRepository);
        var user = TestDataFactory.user(1L);
        var car = TestDataFactory.carVersion(2L);
        car.setTestDriveAvailable(true);
        var dateTime = LocalDateTime.now();
        var testDrive = new TestDrive(user, car, dateTime);
        TestDataFactory.setId(testDrive, 3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.of(car));
        when(testDriveRepository.save(any(TestDrive.class))).thenReturn(testDrive);
        when(testDriveRepository.findById(3L)).thenReturn(Optional.of(testDrive));
        when(testDriveRepository.findByClientId(1L)).thenReturn(Optional.of(testDrive));
        when(testDriveRepository.findAll()).thenReturn(List.of(testDrive));
        when(carVersionRepository.findByTestDriveAvailable(true)).thenReturn(List.of(car));

        assertEquals(3L, service.create(1L, 2L, dateTime));
        assertEquals(Optional.of(testDrive), service.findById(3L));
        assertEquals(Optional.of(testDrive), service.findByClientId(1L));
        assertEquals(1, service.findAll().size());
        assertEquals(List.of(2L), service.findAllCars());

        service.addCar(2L);
        assertTrue(car.isTestDriveAvailable());

        service.removeCar(2L);
        assertFalse(car.isTestDriveAvailable());

        service.deleteById(3L);
        verify(testDriveRepository).deleteById(3L);
    }

    @Test
    void shouldThrowForCreateWhenUserOrCarMissingOrCarUnavailable() {
        TestDriveService service = new TestDriveService(testDriveRepository, userRepository, carVersionRepository);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.create(1L, 2L, LocalDateTime.now()));

        when(userRepository.findById(1L)).thenReturn(Optional.of(TestDataFactory.user(1L)));
        when(carVersionRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CarVersionNotFoundException.class, () -> service.create(1L, 2L, LocalDateTime.now()));

        var unavailableCar = TestDataFactory.carVersion(2L);
        unavailableCar.setTestDriveAvailable(false);
        when(carVersionRepository.findById(2L)).thenReturn(Optional.of(unavailableCar));

        assertThrows(CarVersionNotAvailableForTestDrive.class, () -> service.create(1L, 2L, LocalDateTime.now()));
    }

    @Test
    void shouldThrowWhenAddingOrRemovingUnknownCar() {
        TestDriveService service = new TestDriveService(testDriveRepository, userRepository, carVersionRepository);
        when(carVersionRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CarVersionNotFoundException.class, () -> service.addCar(2L));
        assertThrows(CarVersionNotFoundException.class, () -> service.removeCar(2L));
    }
}
