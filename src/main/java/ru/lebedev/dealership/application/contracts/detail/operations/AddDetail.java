package ru.lebedev.dealership.application.contracts.detail.operations;

import ru.lebedev.dealership.application.contracts.detail.models.DetailInputDto;

public final class AddDetail {
    private AddDetail() {
    }

    public record Request(String userId, DetailInputDto inputDto) {
    }

    public sealed interface Response permits Success, Failure {
    }

    public record Success(String detailId) implements Response {
    }

    public record Failure(String message) implements Response {
    }
}
