package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.AddCarHeadUseCase;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.AddCarHead;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class AddCarHeadService implements AddCarHeadUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarHeadRepository carHeadRepository;

    public AddCarHeadService(UserRepository userRepository, CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository, Permission permission) {
        this.userRepository = userRepository;
        this.carHeadRepository = carHeadRepository;
        this.permission = permission;
    }

    @Override
    public AddCarHead.Response add(AddCarHead.Request request) {
        var userId = new UserId(UUID.fromString(request.inputDto().userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new AddCarHead.Failure("The user doesn't have the permission to create cars");
        }

        var carHeadId = new CarHeadId(UUID.randomUUID());
        CarHead carHead = CarHeadDtoMapper.map(carHeadId, request.inputDto());
        carHead = carHeadRepository.save(carHead);

        return new AddCarHead.Success(carHead.id().value().toString());
    }
}
