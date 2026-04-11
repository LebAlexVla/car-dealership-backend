package ru.lebedev.dealership.controller.testdrive.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.testdrive.dto.TestDriveOutputDto;
import ru.lebedev.dealership.domain.testdrive.TestDrive;

@Mapper(componentModel = "spring")
public interface TestDriveMapper {
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "carVersionId", source = "carVersion.id")
    TestDriveOutputDto toDto(TestDrive testDrive);
}
