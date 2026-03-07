package ru.lebedev.dealership.application.services.testdrive;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.TestDriveRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.testdrive.ShowTestDrivesUseCase;
import ru.lebedev.dealership.application.contracts.testdrive.mappers.TestDriveOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.testdrive.operations.ShowTestDrives;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.List;
import java.util.UUID;

public class ShowTestDrivesService implements ShowTestDrivesUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final TestDriveRepository testDriveRepository;

    public ShowTestDrivesService(Permission permission, UserRepository userRepository, TestDriveRepository testDriveRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.testDriveRepository = testDriveRepository;
    }

    @Override
    public ShowTestDrives.Response show(ShowTestDrives.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowTestDrives.Failure("The user doesn't have the permission to show test drives");
        }

        List<TestDrive> testDrives = testDriveRepository.findAll();

        return new ShowTestDrives.Success(testDrives
                .stream()
                .map(TestDriveOutputDtoMapper::map)
                .toList()
        );
    }
}