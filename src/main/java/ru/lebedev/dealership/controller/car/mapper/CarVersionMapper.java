package ru.lebedev.dealership.controller.car.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.application.filters.CarVersionFilter;
import ru.lebedev.dealership.controller.car.dto.CarVersionFilterDto;
import ru.lebedev.dealership.controller.car.dto.CarVersionInputDto;
import ru.lebedev.dealership.controller.car.dto.CarVersionOutputDto;
import ru.lebedev.dealership.controller.car.dto.EngineDto;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.vo.Engine;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;

@Mapper(componentModel = "spring", uses = PriceMapper.class)
public interface CarVersionMapper {
    @Mapping(target = "carHead", ignore = true)
    @Mapping(target = "testDriveAvailable", ignore = true)
    @Mapping(target = "engine", source = "engineDto")
    CarVersion toEntity(CarVersionInputDto dto);

    @Mapping(target = "carHeadId", source = "carHead.id")
    @Mapping(target = "engineDto", source = "engine")
    CarVersionOutputDto toDto(CarVersion carVersion);

    CarVersionFilter toFilter(CarVersionFilterDto dto);

    Engine toEngine(EngineDto dto);

    EngineDto toEngineDto(Engine engine);

    default Long mapPower(EnginePower power) {
        return power.getHorsepower();
    }

    default double mapCapacity(EngineCapacity capacity) {
        return capacity.getLiters();
    }

    default EnginePower mapToPower(Long horsepower) {
        return new EnginePower(horsepower);
    }

    default EngineCapacity mapToCapacity(double liters) {
        return new EngineCapacity(liters);
    }
}