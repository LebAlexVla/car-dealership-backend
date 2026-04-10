package ru.lebedev.dealership.controller.user.mapper;

import org.mapstruct.Mapper;
import ru.lebedev.dealership.controller.user.dto.UserInputDto;
import ru.lebedev.dealership.controller.user.dto.UserOutputDto;
import ru.lebedev.dealership.domain.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserInputDto dto);

    UserOutputDto toDto(User user);
}