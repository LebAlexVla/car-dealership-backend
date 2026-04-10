package ru.lebedev.dealership.controller.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.filters.CarHeadFilter;
import ru.lebedev.dealership.application.filters.CarVersionFilter;
import ru.lebedev.dealership.application.services.CarService;
import ru.lebedev.dealership.controller.car.dto.*;
import ru.lebedev.dealership.controller.car.mapper.CarHeadMapper;
import ru.lebedev.dealership.controller.car.mapper.CarVersionMapper;
import ru.lebedev.dealership.domain.car.entities.CarHead;
import ru.lebedev.dealership.domain.car.entities.CarVersion;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final CarHeadMapper carHeadMapper;
    private final CarVersionMapper carVersionMapper;

    public CarController(CarService carService, CarHeadMapper carHeadMapper, CarVersionMapper carVersionMapper) {
        this.carService = carService;
        this.carHeadMapper = carHeadMapper;
        this.carVersionMapper = carVersionMapper;
    }

    @PostMapping("/head")
    public ResponseEntity<Long> createHead(@RequestBody CarHeadInputDto carHeadInputDto) {
        CarHead carHead = carHeadMapper.toEntity(carHeadInputDto);
        Long id = carService.createHead(carHead);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/version")
    public ResponseEntity<Long> createVersion(@RequestBody CarVersionInputDto carVersionInputDto) {
        CarVersion carVersion = carVersionMapper.toEntity(carVersionInputDto);
        Long id = carService.createVersion(carVersion, carVersionInputDto.carHeadId());

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/head/{id}")
    public ResponseEntity<CarHeadOutputDto> findHeadById(@PathVariable("id") Long id) {
        return carService.findHeadById(id)
                .map(carHeadMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<CarVersionOutputDto> findVersionById(@PathVariable("id") Long id) {
        return carService.findVersionById(id)
                .map(carVersionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/head")
    public ResponseEntity<List<CarHeadOutputDto>> findHead(@ModelAttribute CarHeadFilterDto filterDto) {
        CarHeadFilter filter = carHeadMapper.toFilter(filterDto);
        List<CarHeadOutputDto> result = carService.findHead(filter)
                .stream()
                .map(carHeadMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/version")
    public ResponseEntity<List<CarVersionOutputDto>> findVersion(@ModelAttribute CarVersionFilterDto filterDto) {
        CarVersionFilter filter = carVersionMapper.toFilter(filterDto);
        List<CarVersionOutputDto> result = carService.findVersion(filter)
                .stream()
                .map(carVersionMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/head/{id}")
    public ResponseEntity<Void> deleteHead(@PathVariable("id") Long id) {
        carService.deleteHead(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/version/{id}")
    public ResponseEntity<Void> deleteVersion(@PathVariable("id") Long id) {
        carService.deleteVersion(id);
        return ResponseEntity.noContent().build();
    }
}