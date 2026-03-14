package ru.lebedev.dealership.domain.configuration;

import ru.lebedev.dealership.domain.detail.Detail;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.math.BigDecimal;
import java.util.Set;

public record Configuration(Set<Detail> details) {
    public Price calculatePrice() {
        var result = new Price(BigDecimal.ZERO);
        for (var detail : details) {
            result = result.add(detail.price());
        }

        return result;
    }
}