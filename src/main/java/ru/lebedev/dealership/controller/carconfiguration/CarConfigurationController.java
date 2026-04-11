package ru.lebedev.dealership.controller.carconfiguration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.services.CarConfigurationService;
import ru.lebedev.dealership.controller.carconfiguration.dto.CarConfigurationCustomizerOutputDto;
import ru.lebedev.dealership.controller.carconfiguration.dto.CarConfigurationDefaulterOutputDto;
import ru.lebedev.dealership.controller.carconfiguration.dto.CarConfigurationOutputDto;
import ru.lebedev.dealership.controller.carconfiguration.mapper.ConfigurationMapper;
import ru.lebedev.dealership.controller.carconfiguration.mapper.CustomizerMapper;
import ru.lebedev.dealership.controller.carconfiguration.mapper.DefaulterMapper;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/car-configuration")
public class CarConfigurationController {
    private final CarConfigurationService carConfigurationService;
    private final DefaulterMapper defaulterMapper;
    private final CustomizerMapper customizerMapper;
    private final ConfigurationMapper configurationMapper;

    public CarConfigurationController(CarConfigurationService carConfigurationService, DefaulterMapper defaulterMapper, CustomizerMapper customizerMapper, ConfigurationMapper configurationMapper) {
        this.carConfigurationService = carConfigurationService;
        this.defaulterMapper = defaulterMapper;
        this.customizerMapper = customizerMapper;
        this.configurationMapper = configurationMapper;
    }

    @PostMapping("/defaulter")
    ResponseEntity<Long> createDefaulter(
            @RequestParam Long carVersionId, @RequestBody Set<Long> requiredDetailsIds
    ) {
        Long id = carConfigurationService.createDefaulter(carVersionId, requiredDetailsIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/customizer")
    ResponseEntity<Long> createCustomizer(
            @RequestParam Long defaulterId, @RequestParam Long clientId
    ) {
        Long id = carConfigurationService.createCustomizer(defaulterId, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping
    ResponseEntity<Long> createConfiguration(@RequestParam Long customizerId) {
        Long id = carConfigurationService.createCarConfiguration(customizerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/defaulter/by-car-version/{car-version-id}")
    ResponseEntity<CarConfigurationDefaulterOutputDto> findDefaulterByCarVersion(@PathVariable("car-version-id") Long carVersionId) {
        return carConfigurationService.findDefaulterByCarVersionId(carVersionId)
                .map(defaulterMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customizer/{id}")
    ResponseEntity<CarConfigurationCustomizerOutputDto> findCustomizerById(@PathVariable("id") Long id) {
        return carConfigurationService.findCustomizerById(id)
                .map(customizerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    ResponseEntity<CarConfigurationOutputDto> findConfigurationById(@PathVariable("id") Long id) {
        return carConfigurationService.findConfigurationById(id)
                .map(configurationMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/defaulter")
    ResponseEntity<List<CarConfigurationDefaulterOutputDto>> findAllDefaulters() {
        List<CarConfigurationDefaulterOutputDto> result = carConfigurationService.findAllDefaulters()
                .stream()
                .map(defaulterMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/customizer")
    ResponseEntity<List<CarConfigurationCustomizerOutputDto>> findAllCustomizers() {
        List<CarConfigurationCustomizerOutputDto> result = carConfigurationService.findAllCustomizers()
                .stream()
                .map(customizerMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping
    ResponseEntity<List<CarConfigurationOutputDto>> findAllConfigurations() {
        List<CarConfigurationOutputDto> result = carConfigurationService.findAllConfigurations()
                .stream()
                .map(configurationMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/defaulter/{defaulter-id}/required-details")
    public ResponseEntity<Void> addDefaultDetail(@PathVariable("defaulter-id") Long defaulterId, @RequestParam Long detailId) {
        carConfigurationService.addDefaultDetail(defaulterId, detailId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/customizer/{customizer-id}/required-details")
    public ResponseEntity<Void> selectRequiredDetail(@PathVariable("customizer-id") Long customizerId, @RequestParam Long detailId) {
        carConfigurationService.selectCustomRequiredDetail(customizerId, detailId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/customizer/{customizer-id}/optional-details")
    public ResponseEntity<Void> selectOptionalDetail(@PathVariable("customizer-id") Long customizerId, @RequestParam Long detailId) {
        carConfigurationService.selectCustomOptionalDetail(customizerId, detailId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customizer/{customizer-id}/optional-details/{detail-id}")
    public ResponseEntity<Void> rejectOptionalDetail(@PathVariable("customizer-id") Long customizerId, @PathVariable("detail-id") Long detailId) {
        carConfigurationService.rejectCustomOptionalDetail(customizerId, detailId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/defaulter/by-car-version/{car-version-id}")
    public ResponseEntity<Void> deleteDefaulterByCarVersion(@PathVariable("car-version-id") Long carVersionId) {
        carConfigurationService.deleteDefaulterByCarId(carVersionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customizer/{id}")
    public ResponseEntity<Void> deleteCustomizerById(@PathVariable("id") Long id) {
        carConfigurationService.deleteCustomizerById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfigurationById(@PathVariable("id") Long id) {
        carConfigurationService.deleteConfigurationById(id);
        return ResponseEntity.noContent().build();
    }
}