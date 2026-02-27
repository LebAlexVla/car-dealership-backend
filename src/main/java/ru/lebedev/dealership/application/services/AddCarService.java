package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.identity.IdGenerator;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.storageadmin.AddCarHeadUseCase;
import ru.lebedev.dealership.application.contracts.storageadmin.AddCarVersionUseCase;
import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddCarHead;
import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddCarVersion;
import ru.lebedev.dealership.application.permissions.CarAddPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.user.User;

public class AddCarService implements AddCarHeadUseCase, AddCarVersionUseCase {
    private final Permission permission = new CarAddPermission();

    private final UserRepository userRepository;
    private final CarHeadRepository carHeadRepository;
    private final CarVersionRepository carVersionRepository;

    private final IdGenerator idGenerator;

    public AddCarService(UserRepository userRepository, CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository, IdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.carHeadRepository = carHeadRepository;
        this.carVersionRepository = carVersionRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public AddCarHead.Response add(AddCarHead.Request request) {
        User user = userRepository.findById(request.userId());
        if (!permission.check(user.type())) {
            return new AddCarHead.Failure("The user doesn't have the permission to create cars");
        }

        var carHeadId = new CarHeadId(idGenerator.generate());
        var carHead = new CarHead(carHeadId, request.brand(), request.model(), request.bodyType());
        carHead = carHeadRepository.save(carHead);

        return new AddCarHead.Success(carHead.id().value());
    }

    @Override
    public AddCarVersion.Response add(AddCarVersion.Request request) {
        User user = userRepository.findById(request.userId());
        if (!permission.check(user.type())) {
            return new AddCarVersion.Failure("The user doesn't have the permission to create cars");
        }

        var carVersionId = new CarVersionId(idGenerator.generate());
        var carVersion = new CarVersion(
                carVersionId,
                request.carVersionName(),
                request.carHeadId(),
                request.engine(),
                request.gearBoxType(),
                request.carDrive(),
                request.colors()
        );

        carVersion = carVersionRepository.save(carVersion);

        return new AddCarVersion.Success(carVersion.id().value());
    }
}
