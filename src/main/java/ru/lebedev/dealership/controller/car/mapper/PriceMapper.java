package ru.lebedev.dealership.controller.car.mapper;

import org.mapstruct.Mapper;
import ru.lebedev.dealership.domain.car.vo.Price;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    default Price toPrice(BigDecimal rubles) {
        return new Price(rubles);
    }

    default BigDecimal toBigDecimal(Price price) {
        return price.getRubles();
    }
}