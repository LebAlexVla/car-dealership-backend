package ru.lebedev.dealership.application.contracts.detail.operations;

import ru.lebedev.dealership.application.contracts.detail.models.DetailFilterDto;
import ru.lebedev.dealership.application.contracts.detail.models.DetailOutputDto;

import java.util.List;

public final class ShowDetails {
    private ShowDetails() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, DetailFilterDto detailFilterDto) {
    }

    public record Success(List<DetailOutputDto> detailOutputDto) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}
