package ru.lebedev.dealership.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.domain.car.vo.Price;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.List;
import java.util.Set;

public class DetailSpecifications {
    public static Specification<Detail> fromFilter(DetailFilter filter) {
        return Specification
                .where(hasType(filter.detailTypes()))
                .and(minPriceLimit(filter.minPrice()))
                .and(maxPriceLimit(filter.maxPrice()))
                .and(compatibleWithCars(filter.compatibleCarsIds()));
    }

    private static Specification<Detail> hasType(List<String> detailTypes) {
        return (root, query, criteriaBuilder) ->
                (detailTypes == null || detailTypes.isEmpty()) ? null :
                        root.get("type").in(detailTypes);
    }

    private static Specification<Detail> minPriceLimit(Price price) {
        return (root, query, criteriaBuilder) ->
                price == null ? null :
                        criteriaBuilder.ge(root.get("price"), price.getRubles());
    }

    private static Specification<Detail> maxPriceLimit(Price price) {
        return (root, query, criteriaBuilder) ->
                price == null ? null :
                        criteriaBuilder.le(root.get("price"), price.getRubles());
    }

    private static Specification<Detail> compatibleWithCars(Set<Long> compatibleCarsIds) {
        return (compatibleCarsIds == null || compatibleCarsIds.isEmpty()) ? null :
                (root, query, criteriaBuilder) ->
                        root
                                .join("compatibleCars")
                                .get("id")
                                .in(compatibleCarsIds);
    }
}