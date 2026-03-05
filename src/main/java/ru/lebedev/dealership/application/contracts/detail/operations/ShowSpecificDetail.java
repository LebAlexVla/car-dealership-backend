package ru.lebedev.dealership.application.contracts.detail.operations;

import ru.lebedev.dealership.application.contracts.detail.models.DetailOutputDto;

public final class ShowSpecificDetail {
    private ShowSpecificDetail() {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Request(String userId, String detailId) {
    }

    public record Success(DetailOutputDto detailOutputDto) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}
