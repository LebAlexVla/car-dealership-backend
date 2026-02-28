package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.ShowCarsHeadsUseCase;
import ru.lebedev.dealership.application.contracts.car.mappers.CarHeadFilterDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.ShowCarsHeads;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShowCarsHeadsService implements ShowCarsHeadsUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarHeadRepository carHeadRepository;

    public ShowCarsHeadsService(Permission permission, UserRepository userRepository, CarHeadRepository carHeadRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.carHeadRepository = carHeadRepository;
    }

    @Override
    public ShowCarsHeads.Response show(ShowCarsHeads.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowCarsHeads.Failure("The user doesn't have the permission to show cars");
        }

        List<CarHeadId> carHeads = carHeadRepository.findByFilter(
                CarHeadFilterDtoMapper.map(request.filterDto())
        );

        return new ShowCarsHeads.Success(carHeads
                .stream()
                .map(CarHeadId::value)
                .collect(Collectors.toList()));
    }
}
