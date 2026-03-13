package ru.lebedev.dealership.domain.detail;

import ru.lebedev.dealership.domain.car.vo.CarVersionId;
import ru.lebedev.dealership.domain.shared.vo.Price;

import java.util.Set;

public record Detail(DetailId id, String name, DetailType type, Price price, Set<CarVersionId> compatibleCars) {

    public boolean checkCompatibility(CarVersionId carVersionId) {
        return compatibleCars.contains(carVersionId);
    }

    public void addCompatibleCar(CarVersionId carVersionId) {
        compatibleCars.add(carVersionId);
    }

    public void removeCompatibleCar(CarVersionId carVersionId) {
        compatibleCars.remove(carVersionId);
    }
}