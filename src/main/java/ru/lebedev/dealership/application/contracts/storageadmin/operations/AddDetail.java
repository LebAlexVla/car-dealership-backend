package ru.lebedev.dealership.application.contracts.storageadmin.operations;

import ru.lebedev.dealership.domain.car.valueobjects.CarVersionId;
import ru.lebedev.dealership.domain.car.valueobjects.DetailType;
import ru.lebedev.dealership.domain.shared.valueobjects.Price;

import java.util.Set;
import java.util.UUID;

public final class AddDetail {
    private AddDetail() {}

    public record Request(
            UUID userId,
            String detailName,
            DetailType detailType,
            Price price,
            Set<CarVersionId> compatibleCars
    ) {}

    public sealed interface Response permits Success, Failure {}

    public record Success(long detailId) implements Response {}

    public record Failure(String message) implements Response {}
}
