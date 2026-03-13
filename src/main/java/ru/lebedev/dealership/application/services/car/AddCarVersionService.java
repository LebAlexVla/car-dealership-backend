package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.AddCarVersionUseCase;
import ru.lebedev.dealership.application.contracts.car.mappers.CarVersionInputDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.AddCarVersion;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.vo.CarVersionId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class AddCarVersionService implements AddCarVersionUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;

    public AddCarVersionService(Permission permission, UserRepository userRepository, CarVersionRepository carVersionRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
    }

    @Override
    public AddCarVersion.Response add(AddCarVersion.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new AddCarVersion.Failure("The user doesn't have the permission to create cars");
        }

        var carVersionId = new CarVersionId(UUID.randomUUID());
        CarVersion carVersion = CarVersionInputDtoMapper.map(carVersionId, request.inputDto());

        carVersion = carVersionRepository.save(carVersion);

        return new AddCarVersion.Success(carVersion.id().value().toString());
    }
}