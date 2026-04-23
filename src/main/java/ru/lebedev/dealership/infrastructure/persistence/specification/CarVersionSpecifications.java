package ru.lebedev.dealership.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.lebedev.dealership.application.filters.CarVersionFilter;
import ru.lebedev.dealership.domain.car.entities.CarVersion;
import ru.lebedev.dealership.domain.car.enums.CarDrive;
import ru.lebedev.dealership.domain.car.enums.FuelType;
import ru.lebedev.dealership.domain.car.enums.GearboxType;
import ru.lebedev.dealership.domain.car.vo.EngineCapacity;
import ru.lebedev.dealership.domain.car.vo.EnginePower;
import ru.lebedev.dealership.domain.car.vo.Price;

import java.util.List;

public class CarVersionSpecifications {
    public static Specification<CarVersion> fromFilter(CarVersionFilter filter) {
        return Specification
                .where(minPriceLimit(filter.minPrice()))
                .and(maxPriceLimit(filter.maxPrice()))
                .and(hasFuelTypes(filter.fuelTypes()))
                .and(minEnginePowerLimit(filter.minEnginePower()))
                .and(maxEnginePowerLimit(filter.maxEnginePower()))
                .and(minEngineCapacityLimit(filter.minEngineCapacity()))
                .and(maxEngineCapacityLimit(filter.maxEngineCapacity()))
                .and(hasGearboxTypes(filter.gearboxTypes()))
                .and(hasCarDrives(filter.carDrives()))
                .and(hasColors(filter.colors()));
    }

    private static Specification<CarVersion> minPriceLimit(Price price) {
        return (root, query, criteriaBuilder) ->
                price == null ? null :
                        criteriaBuilder.ge(root.get("price"), price.getRubles());
    }

    private static Specification<CarVersion> maxPriceLimit(Price price) {
        return (root, query, criteriaBuilder) ->
                price == null ? null :
                        criteriaBuilder.le(root.get("price"), price.getRubles());
    }

    private static Specification<CarVersion> hasFuelTypes(List<FuelType> fuelTypes) {
        return (root, query, criteriaBuilder) ->
                (fuelTypes == null || fuelTypes.isEmpty()) ? null :
                        root.get("engine").get("fuelType").in(fuelTypes);
    }

    private static Specification<CarVersion> minEnginePowerLimit(EnginePower power) {
        return (root, query, criteriaBuilder) ->
                power == null ? null :
                        criteriaBuilder.ge(root.get("engine").get("power"), power.getHorsepower());
    }

    private static Specification<CarVersion> maxEnginePowerLimit(EnginePower power) {
        return (root, query, criteriaBuilder) ->
                power == null ? null :
                        criteriaBuilder.le(root.get("engine").get("power"), power.getHorsepower());
    }

    private static Specification<CarVersion> minEngineCapacityLimit(EngineCapacity capacity) {
        return (root, query, criteriaBuilder) ->
                capacity == null ? null :
                        criteriaBuilder.ge(root.get("engine").get("capacity"), capacity.getLiters());
    }

    private static Specification<CarVersion> maxEngineCapacityLimit(EngineCapacity capacity) {
        return (root, query, criteriaBuilder) ->
                capacity == null ? null :
                        criteriaBuilder.le(root.get("engine").get("capacity"), capacity.getLiters());
    }

    private static Specification<CarVersion> hasGearboxTypes(List<GearboxType> gearboxTypes) {
        return (root, query, criteriaBuilder) ->
                (gearboxTypes == null || gearboxTypes.isEmpty()) ? null :
                        root.get("gearboxType").in(gearboxTypes);
    }

    private static Specification<CarVersion> hasCarDrives(List<CarDrive> carDrives) {
        return (root, query, criteriaBuilder) ->
                (carDrives == null || carDrives.isEmpty()) ? null :
                        root.get("carDrive").in(carDrives);
    }

    private static Specification<CarVersion> hasColors(List<String> colors) {
        return (root, query, criteriaBuilder) ->
                (colors == null || colors.isEmpty()) ? null :
                        root.join("colors").in(colors);
    }
}