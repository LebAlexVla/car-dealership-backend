package ru.lebedev.dealership.application.services.detail;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.detail.ShowSpecificDetailUseCase;
import ru.lebedev.dealership.application.contracts.detail.mappers.DetailOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.operations.ShowSpecificDetail;
import ru.lebedev.dealership.application.permissions.DetailAddPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.detail.DetailId;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.UUID;

public class ShowSpecificDetailService implements ShowSpecificDetailUseCase {
    private final Permission permission = new DetailAddPermission();

    private final UserRepository userRepository;
    private final DetailRepository detailRepository;

    public ShowSpecificDetailService(UserRepository userRepository, DetailRepository detailRepository) {
        this.userRepository = userRepository;
        this.detailRepository = detailRepository;
    }

    @Override
    public ShowSpecificDetail.Response show(ShowSpecificDetail.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowSpecificDetail.Failure("The user doesn't have the permission to create cars");
        }

        var detailId = new DetailId(UUID.fromString(request.detailId()));
        Detail detail = detailRepository.findById(detailId);

        return new ShowSpecificDetail.Success(DetailOutputDtoMapper.map(detail));
    }
}
