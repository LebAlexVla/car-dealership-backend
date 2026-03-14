package ru.lebedev.dealership.application.contracts.detail;

import ru.lebedev.dealership.application.contracts.detail.models.DetailOutputDto;
import ru.lebedev.dealership.application.contracts.detail.requests.AddDetailRequest;
import ru.lebedev.dealership.application.contracts.detail.requests.DeleteDetailRequest;
import ru.lebedev.dealership.application.contracts.detail.requests.ShowDetailsRequest;
import ru.lebedev.dealership.application.contracts.detail.requests.ShowSpecificDetailRequest;

import java.util.List;

public interface DetailService {
    long addDetail(AddDetailRequest request);

    void deleteDetail(DeleteDetailRequest request);

    DetailOutputDto showSpecificDetail(ShowSpecificDetailRequest request);

    List<DetailOutputDto> showDetails(ShowDetailsRequest request);
}