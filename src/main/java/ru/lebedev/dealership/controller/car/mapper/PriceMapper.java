package ru.lebedev.dealership.controller.car.mapper;

import org.mapstruct.Mapper;
import ru.lebedev.dealership.domain.car.vo.Price;

import java.math.BigDecimal;

@Mapper
public interface PriceMapper {
    Price toPrice(BigDecimal rubles);

    default BigDecimal toBigDecimal(Price price) {
        return price.getRubles();
    }
}