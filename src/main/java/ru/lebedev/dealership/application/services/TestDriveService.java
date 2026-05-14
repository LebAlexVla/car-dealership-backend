package ru.lebedev.dealership.application.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final CurrentUserService currentUserService;

    public TestDriveService(
            TestDriveRepository testDriveRepository,
            UserRepository userRepository,
            CarVersionRepository carVersionRepository,
            CurrentUserService currentUserService
    ) {
        this.testDriveRepository = testDriveRepository;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @testDriveSecurityService.isTestDriveOwner(#id)")
    public Optional<TestDrive> findById(Long id) {
        return testDriveRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<TestDrive> findByClientId(Long clientId) {
        if (currentUserService.hasRole("ADMIN") || currentUserService.hasRole("MANAGER")) {
            return testDriveRepository.findByClientId(clientId);
        }

        Long currentUserId = currentUserService.getCurrentUserId();

        if (!currentUserId.equals(clientId)) {
            return List.of();
        }

        return testDriveRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    public List<TestDrive> findAll() {
        if (currentUserService.hasRole("ADMIN") || currentUserService.hasRole("MANAGER")) {
            return testDriveRepository.findAll();
        }

        return testDriveRepository.findByClientId(currentUserService.getCurrentUserId());
    }

    @Transactional(readOnly = true)
    public List<Long> findAllCars() {
        return carVersionRepository.findByTestDriveAvailable(true).stream()
                .map(CarVersion::getId)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void addCar(Long carVersionId) {
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        carVersion.setTestDriveAvailable(true);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public void removeCar(Long carVersionId) {
        CarVersion carVersion = carVersionRepository.findById(carVersionId)
                .orElseThrow(() -> new CarVersionNotFoundException(carVersionId));
        carVersion.setTestDriveAvailable(false);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @testDriveSecurityService.isTestDriveOwner(#testDriveId)")
    public void deleteById(Long testDriveId) {
        testDriveRepository.deleteById(testDriveId);
    }
}