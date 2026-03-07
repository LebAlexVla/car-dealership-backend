package ru.lebedev.dealership.application.services.testdrive;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.TestDriveRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.testdrive.AddTestDriveUseCase;
import ru.lebedev.dealership.application.contracts.testdrive.mappers.TestDriveInputDtoMapper;
import ru.lebedev.dealership.application.contracts.testdrive.operations.AddTestDrive;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.testdrive.TestDrive;
import ru.lebedev.dealership.domain.testdrive.TestDriveId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class AddTestDriveService implements AddTestDriveUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final TestDriveRepository testDriveRepository;

    public AddTestDriveService(Permission permission, UserRepository userRepository, TestDriveRepository testDriveRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.testDriveRepository = testDriveRepository;
    }

    @Override
    public AddTestDrive.Response add(AddTestDrive.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new AddTestDrive.Failure("The user doesn't have the permission to add test drives");
        }

        var testDriveId = new TestDriveId(UUID.randomUUID());
        TestDrive testDrive = testDriveRepository.save(
                TestDriveInputDtoMapper.map(testDriveId, request.inputDto())
        );

        return new AddTestDrive.Success(testDriveId.value().toString());
    }
}
