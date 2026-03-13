package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.ShowSpecificCarHeadUseCase;
import ru.lebedev.dealership.application.contracts.car.mappers.CarHeadOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.ShowSpecificCarHead;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.vo.CarHeadId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class ShowSpecificCarHeadService implements ShowSpecificCarHeadUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarHeadRepository carHeadRepository;

    public ShowSpecificCarHeadService(Permission permission, UserRepository userRepository, CarHeadRepository carHeadRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.carHeadRepository = carHeadRepository;
    }

    @Override
    public ShowSpecificCarHead.Response show(ShowSpecificCarHead.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowSpecificCarHead.Failure("The user doesn't have the permission to show car");
        }

        var carHeadId = new CarHeadId(UUID.fromString(request.carHeadId()));
        CarHead carHead = carHeadRepository.findById(carHeadId);

        return new ShowSpecificCarHead.Success(CarHeadOutputDtoMapper.map(carHead));
    }
}