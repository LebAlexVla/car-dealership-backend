package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.car.AddCarHeadUseCase;
import ru.lebedev.dealership.application.contracts.car.AddCarVersionUseCase;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadDtoMapper;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionDtoMapper;
import ru.lebedev.dealership.application.contracts.car.operations.AddCarHead;
import ru.lebedev.dealership.application.contracts.car.operations.AddCarVersion;
import ru.lebedev.dealership.application.permissions.CarAddPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.valueobjects.CarHeadId;
import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class AddCarService implements AddCarHeadUseCase, AddCarVersionUseCase {
    private final Permission permission = new CarAddPermission();

    private final UserRepository userRepository;
    private final CarHeadRepository carHeadRepository;
    private final CarVersionRepository carVersionRepository;

    public AddCarService(UserRepository userRepository, CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository) {
        this.userRepository = userRepository;
        this.carHeadRepository = carHeadRepository;
        this.carVersionRepository = carVersionRepository;
    }

    @Override
    public AddCarHead.Response add(AddCarHead.Request request) {
        User user = userRepository.findById(CarHeadDtoMapper.mapUserId(request.inputDto()));
        if (!permission.check(user.type())) {
            return new AddCarHead.Failure("The user doesn't have the permission to create cars");
        }

        var carHeadId = new CarHeadId(UUID.randomUUID());
        var carHead = new CarHead(
                carHeadId,
                CarHeadDtoMapper.mapBrand(request.inputDto()),
                CarHeadDtoMapper.mapModel(request.inputDto()),
                CarHeadDtoMapper.mapBodyType(request.inputDto())
        );
        carHead = carHeadRepository.save(carHead);

        return new AddCarHead.Success(carHead.id().value().toString());
    }

    @Override
    public AddCarVersion.Response add(AddCarVersion.Request request) {
        User user = userRepository.findById(CarVersionDtoMapper.mapUserId(request.inputDto()));
        if (!permission.check(user.type())) {
            return new AddCarVersion.Failure("The user doesn't have the permission to create cars");
        }

        var carVersionId = new CarVersionId(UUID.randomUUID());
        var carVersion = new CarVersion(
                carVersionId,
                request.inputDto().carVersionName(),
                CarVersionDtoMapper.mapCarHeadId(request.inputDto()),
                CarVersionDtoMapper.mapEngine(request.inputDto()),
                CarVersionDtoMapper.mapGearboxType(request.inputDto()),
                CarVersionDtoMapper.mapCarDrive(request.inputDto()),
                CarVersionDtoMapper.mapColors(request.inputDto()),
                CarVersionDtoMapper.mapPrice(request.inputDto())
        );

        carVersion = carVersionRepository.save(carVersion);

        return new AddCarVersion.Success(carVersion.id().value().toString());
    }
}
