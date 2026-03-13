package ru.lebedev.dealership.domain.detail;

import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.Set;

public record Detail(long detailId, String name, DetailType type, Price price, Set<Long> compatibleCars) {

    public boolean checkCompatibility(long carVersionId) {
        return compatibleCars.contains(carVersionId);
    }

    public void addCompatibleCar(long carVersionId) {
        compatibleCars.add(carVersionId);
    }

    public void removeCompatibleCar(long carVersionId) {
        compatibleCars.remove(carVersionId);
    }
}