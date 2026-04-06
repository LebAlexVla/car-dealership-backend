package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import ru.lebedev.dealership.application.exceptions.CarVersionNotFoundException;
import ru.lebedev.dealership.application.exceptions.UserNotFoundException;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.testdrive.TestDriveCar;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.infrastructure.persistence.repository.CarVersionRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveCarsRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveRepository;
import ru.lebedev.dealership.infrastructure.persistence.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TestDriveService {
    private final TestDriveRepository testDriveRepository;
    private final TestDriveCarsRepository testDriveCarsRepository;
    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;

    public TestDriveService(TestDriveRepository testDriveRepository, TestDriveCarsRepository testDriveCarsRepository, UserRepository userRepository, CarVersionRepository carVersionRepository) {
        this.testDriveRepository = testDriveRepository;
        this.testDriveCarsRepository = testDriveCarsRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
    }

    public Long create(Long clientId, Long carVersionId, LocalDateTime dateTime) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        TestDrive savedTestDrive = testDriveRepository.save(new TestDrive(client, carVersion, dateTime));

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

    public List<Long> findAllCars() {
        List<TestDriveCar> cars = testDriveCarsRepository.findAll();
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