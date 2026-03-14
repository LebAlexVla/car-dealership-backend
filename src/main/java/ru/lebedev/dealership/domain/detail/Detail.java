package ru.lebedev.dealership.domain.detail;

import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.Set;

public record Detail(Long detailId, String name, DetailType type, Price price, Set<Long> compatibleCars) {

    public boolean checkCompatibility(Long carVersionId) {
        return compatibleCars.contains(carVersionId);
    }

    public void addCompatibleCar(Long carVersionId) {
        compatibleCars.add(carVersionId);
    }

    public void removeCompatibleCar(Long carVersionId) {
        compatibleCars.remove(carVersionId);
    }
}