package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.ShowSpecificCarVersionUseCase;
import ru.lebedev.dealership.application.contracts.car.mappers.CarVersionOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.ShowSpecificCarVersion;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class ShowSpecificCarVersionService implements ShowSpecificCarVersionUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;

    public ShowSpecificCarVersionService(Permission permission, UserRepository userRepository, CarVersionRepository carVersionRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
    }

    @Override
    public ShowSpecificCarVersion.Response show(ShowSpecificCarVersion.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowSpecificCarVersion.Failure("The user doesn't have the permission to show cars");
        }

        var carVersionId = new CarVersionId(UUID.fromString(request.carVersionId()));
        CarVersion carVersion = carVersionRepository.findById(carVersionId);

        return new ShowSpecificCarVersion.Success(CarVersionOutputDtoMapper.map(carVersion));
    }
}