package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.ShowCarsVersionsUseCase;
import ru.lebedev.dealership.application.contracts.car.mappers.CarVersionFilterDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.ShowCarsVersions;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.List;
import java.util.UUID;

public class ShowCarsVersionsService implements ShowCarsVersionsUseCase {
    private final Permission permission;

    private final UserRepository userRepository;
    private final CarVersionRepository carVersionRepository;

    public ShowCarsVersionsService(Permission permission, UserRepository userRepository, CarVersionRepository carVersionRepository) {
        this.permission = permission;
        this.userRepository = userRepository;
        this.carVersionRepository = carVersionRepository;
    }

    @Override
    public ShowCarsVersions.Response show(ShowCarsVersions.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowCarsVersions.Failure("The user doesn't have the permission to show cars");
        }

        List<CarVersion> carVersions = carVersionRepository.findByFilter(
                CarVersionFilterDtoMapper.map(request.filterDto())
        );

        return new ShowCarsVersions.Success(carVersions);
    }
}
