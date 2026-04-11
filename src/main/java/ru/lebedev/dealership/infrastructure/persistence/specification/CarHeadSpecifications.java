package ru.lebedev.dealership.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lebedev.dealership.application.filters.CarHeadFilter;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.enums.BodyType;

import java.util.List;

public class CarHeadSpecifications {
    public static Specification<CarHead> fromFilter(CarHeadFilter filter) {
        return Specification
                .where(hasBrands(filter.brands()))
                .and(hasCarModels(filter.carModels()))
                .and(hasBodyTypes(filter.bodyTypes()));
    }

    private static Specification<CarHead> hasBrands(List<String> brands) {
        return (root, query, criteriaBuilder) ->
                (brands == null || brands.isEmpty()) ? null :
                        root.get("brand").in(brands);
    }

    private static Specification<CarHead> hasCarModels(List<String> carModels) {
        return (root, query, criteriaBuilder) ->
                (carModels == null || carModels.isEmpty()) ? null :
                        root.get("model").in(carModels);
    }

    private static Specification<CarHead> hasBodyTypes(List<BodyType> bodyTypes) {
        return (root, query, criteriaBuilder) ->
                (bodyTypes == null || bodyTypes.isEmpty()) ? null :
                        root.get("body_type").in(bodyTypes);
    }
}