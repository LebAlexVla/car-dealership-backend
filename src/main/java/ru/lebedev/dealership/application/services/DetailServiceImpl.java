package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.queries.DetailFilter;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.DetailRepository;
import ru.lebedev.dealership.application.contracts.detail.mappers.DetailFilterDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.mappers.DetailInputDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.mappers.DetailOutputDtoMapper;
import ru.lebedev.dealership.application.contracts.detail.models.DetailOutputDto;
import ru.lebedev.dealership.application.contracts.detail.requests.AddDetailRequest;
import ru.lebedev.dealership.application.contracts.detail.requests.DeleteDetailRequest;
import ru.lebedev.dealership.application.contracts.detail.requests.ShowDetailsRequest;
import ru.lebedev.dealership.application.contracts.detail.requests.ShowSpecificDetailRequest;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.List;

public class DetailServiceImpl implements ru.lebedev.dealership.application.contracts.detail.DetailService {
    private final DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public Long addDetail(AddDetailRequest request) {
        Detail detail = DetailInputDtoMapper.map(request.inputDto());
        detail = detailRepository.save(detail);

        return detail.detailId();
    }

    @Override
    public void deleteDetail(DeleteDetailRequest request) {
        Long detailId = request.detailId();
        detailRepository.delete(detailId);
    }

    @Override
    public DetailOutputDto showSpecificDetail(ShowSpecificDetailRequest request) {
        Long detailId = request.detailId();
        Detail detail = detailRepository.findById(detailId);

        return DetailOutputDtoMapper.map(detail);
    }

    @Override
    public List<DetailOutputDto> showDetails(ShowDetailsRequest request) {
        DetailFilter detailFilter = DetailFilterDtoMapper.map(request.detailFilterDto());
        List<Detail> details = detailRepository.findByFilter(detailFilter);

        return details
                .stream()
                .map(DetailOutputDtoMapper::map)
                .toList();
    }
}