package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.ShowCarsHeadsUseCase;
import ru.lebedev.dealership.application.contracts.car.ShowCarsVersionsUseCase;
import ru.lebedev.dealership.application.contracts.car.operations.AddCarHead;
import ru.lebedev.dealership.application.contracts.car.operations.ShowCarsHeads;
import ru.lebedev.dealership.application.contracts.car.operations.ShowCarsVersions;
import ru.lebedev.dealership.application.permissions.CarShowPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.user.User;

import java.util.List;

public class ShowCarsService implements ShowCarsHeadsUseCase, ShowCarsVersionsUseCase {
    private final Permission permission = new CarShowPermission();

    private final UserRepository userRepository;
    private final CarHeadRepository carHeadRepository;
    private final CarVersionRepository carVersionRepository;

    public ShowCarsService(UserRepository userRepository, CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository) {
        this.userRepository = userRepository;
        this.carHeadRepository = carHeadRepository;
        this.carVersionRepository = carVersionRepository;
    }

    @Override
    public ShowCarsHeads.Response show(ShowCarsHeads.Request request) {
        User user = userRepository.findById(request.userId());
        if (!permission.check(user.type())) {
            return new ShowCarsHeads.Failure("The user doesn't have the permission to show cars");
        }

        List<CarHead> carHeads = carHeadRepository.findByFilter(request.filter());

        return new ShowCarsHeads.Success(carHeads);
    }

    @Override
    public ShowCarsVersions.Response show(ShowCarsVersions.Request request) {
        User user = userRepository.findById(request.userId());
        if (!permission.check(user.type())) {
            return new ShowCarsVersions.Failure("The user doesn't have the permission to show cars");
        }

        List<CarVersion> carVersions = carVersionRepository.findByFilter(request.filter());

        return new ShowCarsVersions.Success(carVersions);
    }
}
