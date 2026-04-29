package ru.lebedev.dealership.controller.order.configuredcar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.services.ConfiguredCarOrderService;
import ru.lebedev.dealership.controller.order.configuredcar.dto.ConfiguredCarOrderOutputDto;
import ru.lebedev.dealership.controller.order.configuredcar.mapper.ConfiguredCarOrderMapper;

import java.util.List;

@RestController
@RequestMapping("/congigured-order")
public class ConfiguredCarOrderController {
    private final ConfiguredCarOrderService configuredCarOrderService;
    private final ConfiguredCarOrderMapper configuredCarOrderMapper;

    public ConfiguredCarOrderController(ConfiguredCarOrderService configuredCarOrderService, ConfiguredCarOrderMapper configuredCarOrderMapper) {
        this.configuredCarOrderService = configuredCarOrderService;
        this.configuredCarOrderMapper = configuredCarOrderMapper;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestParam Long carConfigurationId) {
        Long id = configuredCarOrderService.create(carConfigurationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfiguredCarOrderOutputDto> findById(@PathVariable("id") Long id) {
        return configuredCarOrderService.findById(id)
                .map(configuredCarOrderMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-client/{client-id}")
    public ResponseEntity<List<ConfiguredCarOrderOutputDto>> findByClientId(@PathVariable("client-id") Long clientId) {
        List<ConfiguredCarOrderOutputDto> result = configuredCarOrderService.findByClientId(clientId)
                .stream()
                .map(configuredCarOrderMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ConfiguredCarOrderOutputDto>> findAll() {
        List<ConfiguredCarOrderOutputDto> result = configuredCarOrderService.findAll()
                .stream()
                .map(configuredCarOrderMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable("id") Long id) {
        configuredCarOrderService.approveOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> pay(@PathVariable("id") Long id) {
        configuredCarOrderService.payOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<Void> deliver(@PathVariable("id") Long id) {
        configuredCarOrderService.deliverOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable("id") Long id) {
        configuredCarOrderService.completeOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        configuredCarOrderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Long id) {
        configuredCarOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}