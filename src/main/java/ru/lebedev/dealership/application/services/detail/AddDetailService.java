package ru.lebedev.dealership.application.services.detail;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.detail.AddDetailUseCase;
import ru.lebedev.dealership.application.contracts.detail.models.DetailDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.operations.AddDetail;
import ru.lebedev.dealership.application.permissions.DetailAddPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailId;
import ru.lebedev.dealership.domain.user.User;

import java.util.UUID;

public class AddDetailService implements AddDetailUseCase {
    private final Permission permission = new DetailAddPermission();

    private final UserRepository userRepository;
    private final DetailRepository detailRepository;

    public AddDetailService(UserRepository userRepository, DetailRepository detailRepository) {
        this.userRepository = userRepository;
        this.detailRepository = detailRepository;
    }

    @Override
    public AddDetail.Response add(AddDetail.Request request) {
        User user = userRepository.findById(DetailDtoMapper.mapUserId(request.inputDto()));
        if (!permission.check(user.type())) {
            return new AddDetail.Failure("The user doesn't have the permission to create cars");
        }

        var detailId = new DetailId(UUID.randomUUID());
        var detail = new Detail(
                detailId,
                request.inputDto().detailName(),
                DetailDtoMapper.mapDetailType(request.inputDto()),
                DetailDtoMapper.mapPrice(request.inputDto()),
                DetailDtoMapper.mapCompatibleCars(request.inputDto())
        );

        detail = detailRepository.save(detail);

        return new AddDetail.Success(detail.getId().value().toString());
    }
}
