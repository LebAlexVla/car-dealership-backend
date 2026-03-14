package ru.lebedev.dealership.application.contracts.carconfiguration.mappers;

import ru.lebedev.dealership.application.contracts.carconfiguration.models.CarConfigurationDefaulterInputDto;
import ru.lebedev.dealership.domain.carconfiguration.CarConfigurationDefaulter;
import ru.lebedev.dealership.domain.detail.DetailType;

import java.util.Map;
import java.util.stream.Collectors;

public class CarConfigurationDefaulterInputDtoMapper {
    public static CarConfigurationDefaulter map(CarConfigurationDefaulterInputDto inputDto) {
        return new CarConfigurationDefaulter(
                0L,
                inputDto.carVersionId(),
                inputDto.requiredDetails()
                        .entrySet()
                        .stream()
                        .map(entry -> Map.entry(
                                new DetailType(entry.getKey()),
                                entry.getValue()
                        ))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ))
        );
    }
}