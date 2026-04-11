package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
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

@Service
public class TestDriveService {
    private final TestDriveRepository testDriveRepository;
    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;

    public TestDriveService(TestDriveRepository testDriveRepository, UserRepository userRepository, CarVersionRepository carVersionRepository) {
        this.testDriveRepository = testDriveRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
    }

    public Long create(Long clientId, Long carVersionId, LocalDateTime dateTime) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        if (!carVersion.isTestDriveAvailable()) {
            throw new CarVersionNotAvailableForTestDrive(carVersion.getId());
        }
        TestDrive savedTestDrive = testDriveRepository.save(new TestDrive(client, carVersion, dateTime));

        return savedTestDrive.getId();
    }

    public Optional<TestDrive> findById(Long id) {
        return testDriveRepository.findById(id);
    }

    public Optional<TestDrive> findByClientId(Long clientId) {
        return testDriveRepository.findByClientId(clientId);
    }

    public List<TestDrive> findAll() {
        return testDriveRepository.findAll();
    }

    public List<Long> findAllCars() {
        return carVersionRepository.findByTestDriveAvailable(true).stream()
                .map(CarVersion::getId)
                .toList();
    }

    public void addCar(Long carVersionId) {
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        carVersion.setTestDriveAvailable(true);
    }

    public void removeCar(Long carVersionId) {
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        carVersion.setTestDriveAvailable(false);
    }

    public void deleteById(Long testDriveId) {
        testDriveRepository.deleteById(testDriveId);
    }
}