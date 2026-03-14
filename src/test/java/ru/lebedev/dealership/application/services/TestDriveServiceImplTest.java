package ru.lebedev.dealership.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.TestDriveCarsRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.TestDriveRepository;
import ru.lebedev.dealership.application.contracts.testdrive.requests.AddCarsRequest;
import ru.lebedev.dealership.application.contracts.testdrive.requests.DeleteTestDriveRequest;
import ru.lebedev.dealership.application.contracts.testdrive.requests.RemoveCarsRequest;
import ru.lebedev.dealership.application.contracts.testdrive.requests.ShowTestDrivesRequest;

import java.util.List;

import static org.mockito.Mockito.*;

class TestDriveServiceImplTest {

    private TestDriveRepository repository;
    private TestDriveCarsRepository carsRepository;
    private TestDriveServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(TestDriveRepository.class);
        carsRepository = mock(TestDriveCarsRepository.class);
        service = new TestDriveServiceImpl(repository, carsRepository);
    }

    @Test
    void deleteTestDrive_shouldDelete() {

        service.deleteTestDrive(new DeleteTestDriveRequest(5L));

        verify(repository).delete(5L);
    }

    @Test
    void showTestDrives_shouldCallRepository() {

        when(repository.findAll()).thenReturn(List.of());

        service.showTestDrives(new ShowTestDrivesRequest());

        verify(repository).findAll();
    }

    @Test
    void addCars_shouldCallRepository() {

        service.addCars(new AddCarsRequest(1L,List.of(2L,3L)));

        verify(carsRepository).add(any());
    }

    @Test
    void removeCars_shouldCallRepository() {

        service.removeCars(new RemoveCarsRequest(1L,List.of(2L)));

        verify(carsRepository).delete(any());
    }
}