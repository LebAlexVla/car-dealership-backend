package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.TestDriveCarsRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.TestDriveRepository;
import ru.lebedev.dealership.application.contracts.testdrive.TestDriveUseCase;
import ru.lebedev.dealership.application.contracts.testdrive.mappers.TestDriveInputDtoMapper;
import ru.lebedev.dealership.application.contracts.testdrive.mappers.TestDriveOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.testdrive.models.TestDriveOutputDto;
import ru.lebedev.dealership.application.contracts.testdrive.requests.*;
import ru.lebedev.dealership.domain.testdrive.TestDrive;

import java.util.List;

public class TestDriveService implements TestDriveUseCase {
    private final TestDriveRepository testDriveRepository;
    private final TestDriveCarsRepository testDriveCarsRepository;

    public TestDriveService(TestDriveRepository testDriveRepository, TestDriveCarsRepository testDriveCarsRepository) {
        this.testDriveRepository = testDriveRepository;
        this.testDriveCarsRepository = testDriveCarsRepository;
    }

    @Override
    public long AddTestDrive(AddTestDriveRequest request) {
        TestDrive testDrive = TestDriveInputDtoMapper.map(request.inputDto());
        testDrive = testDriveRepository.save(testDrive);

        return testDrive.testDriveId();
    }

    @Override
    public void DeleteTestDrive(DeleteTestDriveRequest request) {
        long testDriveId = request.testDriveId();
        testDriveRepository.delete(testDriveId);
    }

    @Override
    public List<TestDriveOutputDto> ShowTestDrives(ShowTestDrivesRequest request) {
        List<TestDrive> testDrives = testDriveRepository.findAll();

        return testDrives
                .stream()
                .map(TestDriveOutputDtoMapper::map)
                .toList();
    }

    @Override
    public void AddCars(AddCarsRequest request) {
        long testDriveId = request.testDriveId();
        List<Long> carVersionsIds = request.carVersionsIds();
        testDriveCarsRepository.add(carVersionsIds);
    }

    @Override
    public void RemoveCars(RemoveCarsRequest request) {
        long testDriveId = request.testDriveId();
        List<Long> carVersionsIds = request.carVersionsIds();
        testDriveCarsRepository.delete(carVersionsIds);
    }
}