package ru.lebedev.dealership.application.contracts.detail.requests;

import ru.lebedev.dealership.application.contracts.detail.models.DetailInputDto;

public record AddDetailRequest(DetailInputDto inputDto) {
}
