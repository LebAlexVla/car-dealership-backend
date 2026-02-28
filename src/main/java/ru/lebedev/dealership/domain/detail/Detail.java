package ru.lebedev.dealership.domain.detail;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

import java.util.Set;

public class Detail {
    private final DetailId id;
    private final String name;
    private final DetailType type;
    private final Price price;

    private final Set<CarVersionId> compatibleCars;

    public Detail(DetailId id, String name, DetailType type, Price price, Set<CarVersionId> compatibleCars) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.compatibleCars = compatibleCars;
    }


    public DetailId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DetailType getType() {
        return type;
    }

    public Price getPrice() {
        return price;
    }

    public boolean checkCompatibility(CarVersionId carVersionId) {
        return compatibleCars.contains(carVersionId);
    }

    public Set<CarVersionId> getCompatibleCars() {
        return compatibleCars;
    }

    public void addCompatibleCar(CarVersionId carVersionId) {
        compatibleCars.add(carVersionId);
    }

    public void removeCompatibleCar(CarVersionId carVersionId) {
        compatibleCars.remove(carVersionId);
    }
}