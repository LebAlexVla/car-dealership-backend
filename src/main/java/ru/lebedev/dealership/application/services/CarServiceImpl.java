package ru.lebedev.dealership.application.services;

import ru.lebedev.dealership.application.abstractions.persistence.queries.CarHeadFilter;
import ru.lebedev.dealership.application.abstractions.persistence.queries.CarVersionFilter;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.contracts.car.CarUseCase;
import ru.lebedev.dealership.application.contracts.car.mappers.*;
import ru.lebedev.dealership.application.contracts.car.models.CarHeadOutputDto;
import ru.lebedev.dealership.application.contracts.car.models.CarVersionOutputDto;
import ru.lebedev.dealership.application.contracts.car.requests.*;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

import java.util.List;

public class CarServiceImpl implements CarUseCase {
    private final CarHeadRepository carHeadRepository;
    private final CarVersionRepository carVersionRepository;

    public CarServiceImpl(CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository) {
        this.carHeadRepository = carHeadRepository;
        this.carVersionRepository = carVersionRepository;
    }

    @Override
    public long AddCarHead(AddCarHeadRequest request) {
        CarHead carHead = CarHeadInputDtoMapper.map(request.inputDto());
        carHead = carHeadRepository.save(carHead);

        return carHead.carHeadId();
    }

    @Override
    public long AddCarVersion(AddCarVersionRequest request) {
        CarVersion carVersion = CarVersionInputDtoMapper.map(request.inputDto());
        carVersion = carVersionRepository.save(carVersion);

        return carVersion.carVersionId();
    }

    @Override
    public void DeleteCarHead(DeleteCarHeadRequest request) {
        long carHeadId = request.carHeadId();
        carHeadRepository.delete(carHeadId);
    }

    @Override
    public void DeleteCarVersion(DeleteCarVersionRequest request) {
        long carVersionId = request.carVersionId();
        carVersionRepository.delete(carVersionId);
    }

    @Override
    public CarHeadOutputDto ShowSpecificCarHead(ShowSpecificCarHeadRequest request) {
        long carHeadId = request.carHeadId();
        CarHead carHead = carHeadRepository.findById(carHeadId);

        return CarHeadOutputDtoMapper.map(carHead);
    }

    @Override
    public CarVersionOutputDto ShowSpecificCarVersion(ShowSpecificCarVersionRequest request) {
        long carVersionId = request.carVersionId();
        CarVersion carVersion = carVersionRepository.findById(carVersionId);

        return CarVersionOutputDtoMapper.map(carVersion);
    }

    @Override
    public List<CarHeadOutputDto> ShowCarsHeads(ShowCarsHeadsRequest request) {
        CarHeadFilter carHeadFilter = CarHeadFilterDtoMapper.map(request.filterDto());
        List<CarHead> carHeads = carHeadRepository.findByFilter(carHeadFilter);

        return carHeads
                .stream()
                .map(CarHeadOutputDtoMapper::map)
                .toList();
    }

    @Override
    public List<CarVersionOutputDto> ShowCarsVersions(ShowCarsVersionsRequest request) {
        CarVersionFilter carVersionFilter = CarVersionFilterDtoMapper.map(request.filterDto());
        List<CarVersion> carVersions = carVersionRepository.findByFilter(carVersionFilter);

        return carVersions
                .stream()
                .map(CarVersionOutputDtoMapper::map)
                .toList();
    }
}