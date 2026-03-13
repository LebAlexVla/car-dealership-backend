package ru.lebedev.dealership.application.contracts.detail.requests;

import ru.lebedev.dealership.application.contracts.detail.models.DetailFilterDto;

public record ShowDetailsRequest(DetailFilterDto detailFilterDto) {
}
