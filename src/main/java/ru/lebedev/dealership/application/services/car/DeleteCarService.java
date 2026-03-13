package ru.lebedev.dealership.application.services.car;

import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarHeadRepository;
import ru.lebedev.dealership.application.abstractions.persistence.repositories.CarVersionRepository;
import ru.lebedev.dealership.application.contracts.car.DeleteCarUseCase;
import ru.lebedev.dealership.application.contracts.car.requests.DeleteCarHeadRequest;
import ru.lebedev.dealership.application.contracts.car.requests.DeleteCarVersionRequest;

public class DeleteCarService implements DeleteCarUseCase {
    private final CarHeadRepository carHeadRepository;
    private final CarVersionRepository carVersionRepository;

    public DeleteCarService(CarHeadRepository carHeadRepository, CarVersionRepository carVersionRepository) {
        this.carHeadRepository = carHeadRepository;
        this.carVersionRepository = carVersionRepository;
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
}
