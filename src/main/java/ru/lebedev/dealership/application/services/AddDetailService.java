package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.identity.IdGenerator;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.storageadmin.AddDetailUseCase;
import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddCarHead;
import ru.lebedev.dealership.application.contracts.storageadmin.operations.AddDetail;
import ru.lebedev.dealership.application.permissions.CarAddPermission;
import ru.lebedev.dealership.application.permissions.DetailAddPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.car.entities.Detail;
import ru.lebedev.dealership.domain.car.valueobjects.DetailId;
import ru.lebedev.dealership.domain.user.User;

public class AddDetailService implements AddDetailUseCase {
    private final Permission permission = new DetailAddPermission();

    private final UserRepository userRepository;
    private final DetailRepository detailRepository;

    private final IdGenerator idGenerator;

    public AddDetailService(UserRepository userRepository, DetailRepository detailRepository, IdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.detailRepository = detailRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public AddDetail.Response add(AddDetail.Request request) {
        User user = userRepository.findById(request.userId());
        if (!permission.check(user.type())) {
            return new AddDetail.Failure("The user doesn't have the permission to create cars");
        }

        var detailId = new DetailId(idGenerator.generate());
        var detail = new Detail(
                detailId,
                request.detailName(),
                request.detailType(),
                request.price(),
                request.compatibleCars()
        );

        detail = detailRepository.save(detail);

        return new AddDetail.Success(detail.getId().value());
    }
}
