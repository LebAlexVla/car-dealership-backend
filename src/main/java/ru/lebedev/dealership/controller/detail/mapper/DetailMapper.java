package ru.lebedev.dealership.controller.detail.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.detail.dto.DetailInputDto;
import ru.lebedev.dealership.controller.detail.dto.DetailOutputDto;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface DetailMapper {
    @Mapping(source = "price.rubles", target = "price")
    DetailOutputDto toDto(Detail detail);

    @Mapping(target = "compatibleCars", ignore = true)
    Detail toEntity(DetailInputDto request);

    default Long map(CarVersion carVersion) {
        return carVersion != null ? carVersion.getId() : null;
    }

    default Price map(BigDecimal price) {
        return new Price(price);
    }
}