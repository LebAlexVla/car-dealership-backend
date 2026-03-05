package ru.lebedev.dealership.application.services.detail;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.UserRepository;
import ru.lebedev.dealership.application.contracts.detail.ShowDetailsUseCase;
import ru.lebedev.dealership.application.contracts.detail.mappers.DetailFilterDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.mappers.DetailOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.operations.ShowDetails;
import ru.lebedev.dealership.application.contracts.detail.operations.ShowSpecificDetail;
import ru.lebedev.dealership.application.permissions.DetailAddPermission;
import ru.lebedev.dealership.application.permissions.Permission;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.user.User;
import ru.lebedev.dealership.domain.user.UserId;

import java.util.List;
import java.util.UUID;

public class ShowDetailsService implements ShowDetailsUseCase {
    private final Permission permission = new DetailAddPermission();

    private final UserRepository userRepository;
    private final DetailRepository detailRepository;

    public ShowDetailsService(UserRepository userRepository, DetailRepository detailRepository) {
        this.userRepository = userRepository;
        this.detailRepository = detailRepository;
    }

    @Override
    public ShowDetails.Response show(ShowDetails.Request request) {
        var userId = new UserId(UUID.fromString(request.userId()));
        User user = userRepository.findById(userId);
        if (!permission.check(user.type())) {
            return new ShowDetails.Failure("The user doesn't have the permission to show details");
        }

        List<Detail> details = detailRepository.findByFilter(
                DetailFilterDtoMapper.map(request.detailFilterDto())
        );

        return new ShowDetails.Success(details
                .stream()
                .map(DetailOutputDtoMapper::map)
                .toList()
        );
    }
}