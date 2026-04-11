package ru.lebedev.dealership.controller.order.stock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.dealership.controller.order.stock.dto.StockOrderOutputDto;
import ru.lebedev.dealership.domain.order.stock.StockOrder;

@Mapper(componentModel = "spring")
public interface StockOrderMapper {
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "carVersionId", source = "carVersion.id")
    StockOrderOutputDto toDto(StockOrder order);
}
