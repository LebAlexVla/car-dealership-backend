package ru.lebedev.dealership.controller.testdrive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.services.CurrentUserService;
import ru.lebedev.dealership.application.services.TestDriveService;
import ru.lebedev.dealership.controller.testdrive.dto.TestDriveOutputDto;
import ru.lebedev.dealership.controller.testdrive.mapper.TestDriveMapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/test-drive")
public class TestDriveController {
    private final TestDriveService testDriveService;
    private final TestDriveMapper testDriveMapper;
    private final CurrentUserService currentUserService;

    public TestDriveController(TestDriveService testDriveService, TestDriveMapper testDriveMapper, CurrentUserService currentUserService) {
        this.testDriveService = testDriveService;
        this.testDriveMapper = testDriveMapper;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<Long> create(
            @RequestParam Long carVersionId,
            @RequestParam LocalDateTime dateTime
    ) {
        Long clientId = currentUserService.getCurrentUserId();
        Long id = testDriveService.create(clientId, carVersionId, dateTime);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDriveOutputDto> findById(@PathVariable("id") Long id) {
        return testDriveService.findById(id)
                .map(testDriveMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-client/{client-id}")
    public ResponseEntity<List<TestDriveOutputDto>> findByClientId(@PathVariable("client-id") Long clientId) {
        List<TestDriveOutputDto> result = testDriveService.findByClientId(clientId)
                .stream()
                .map(testDriveMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<TestDriveOutputDto>> findAll() {
        List<TestDriveOutputDto> result = testDriveService.findAll()
                .stream()
                .map(testDriveMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/available-cars")
    public ResponseEntity<List<Long>> findAllCars() {
        return ResponseEntity.ok(testDriveService.findAllCars());
    }

    @PostMapping("/available-cars/{car-version-id}")
    public ResponseEntity<Void> addCar(@PathVariable("car-version-id") Long carVersionId) {
        testDriveService.addCar(carVersionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/available-cars/{car-version-id}")
    public ResponseEntity<Void> removeCar(@PathVariable("car-version-id") Long carVersionId) {
        testDriveService.removeCar(carVersionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long testDriveId) {
        testDriveService.deleteById(testDriveId);
        return ResponseEntity.noContent().build();
    }
}