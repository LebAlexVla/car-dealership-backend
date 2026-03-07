package ru.lebedev.dealership.application.services.testdrive;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarsForTestDriveRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.testdrive.AddCarsForTestDriveUseCase;
import ru.lebedev.dealership.application.contracts.testdrive.operations.AddCarsForTestDrive;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class AddCarsForTestDriveService implements AddCarsForTestDriveUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarsForTestDriveRepository carsForTestDriveRepository;

    public AddCarsForTestDriveService(Permission permission, UserRepository userRepository, CarsForTestDriveRepository carsForTestDriveRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.carsForTestDriveRepository = carsForTestDriveRepository;
    }

    @Override
    public AddCarsForTestDrive.Response add(AddCarsForTestDrive.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new AddCarsForTestDrive.Failure("The user doesn't have the permission to add cars for test drive");
        }

        carsForTestDriveRepository.add(request.carVersionsIds()
                .stream()
                .map(str -> new CarVersionId(UUID.fromString(str)))
                .toList()
        );

        return new AddCarsForTestDrive.Success();
    }
}
