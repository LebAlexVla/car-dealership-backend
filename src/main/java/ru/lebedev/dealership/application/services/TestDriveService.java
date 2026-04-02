package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.testdrive.TestDriveCar;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveCarsRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestDriveService {
    private final TestDriveRepository testDriveRepository;
    private final TestDriveCarsRepository testDriveCarsRepository;

    public TestDriveService(TestDriveRepository testDriveRepository, TestDriveCarsRepository testDriveCarsRepository) {
        this.testDriveRepository = testDriveRepository;
        this.testDriveCarsRepository = testDriveCarsRepository;
    }

    public Long create(TestDrive testDrive) {
        TestDrive savedTestDrive = testDriveRepository.save(testDrive);
        return savedTestDrive.getId();
    }

    public Optional<TestDrive> findById(Long id) {
        return testDriveRepository.findById(id);
    }

    public Optional<TestDrive> findByClientId(Long id) {
        return testDriveRepository.findById(id);
    }

    public List<TestDrive> findAll() {
        return testDriveRepository.findAll();
    }

    public Long addCar(TestDriveCar testDriveCar) {
        TestDriveCar savedCar = testDriveCarsRepository.save(testDriveCar);
        return savedCar.getId();
    }

    public void removeCar(Long testDriveId) {
        testDriveCarsRepository.deleteById(testDriveId);
    }

    public void deleteById(Long testDriveId) {
        testDriveRepository.deleteById(testDriveId);
    }
}