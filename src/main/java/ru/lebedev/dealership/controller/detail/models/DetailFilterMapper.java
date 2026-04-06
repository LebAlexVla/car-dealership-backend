package ru.lebedev.dealership.controller.detail.models;

import org.mapstruct.Mapper;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.math.BigDecimal;

@Mapper
public interface DetailFilterMapper {
    DetailFilter map(DetailFilterDto filterDto);

    default Price map(BigDecimal value) {
        return new Price(value);
    }
}