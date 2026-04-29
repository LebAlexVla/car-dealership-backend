package ru.lebedev.dealership.controller.order.stock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.services.CurrentUserService;
import ru.lebedev.dealership.application.services.StockOrderService;
import ru.lebedev.dealership.controller.order.stock.dto.StockOrderOutputDto;
import ru.lebedev.dealership.controller.order.stock.mapper.StockOrderMapper;

import java.util.List;

@RestController
@RequestMapping("/stock-order")
public class StockOrderController {
    private final StockOrderService stockOrderService;
    private final StockOrderMapper stockOrderMapper;
    private final CurrentUserService currentUserService;

    public StockOrderController(StockOrderService stockOrderService, StockOrderMapper stockOrderMapper, CurrentUserService currentUserService) {
        this.stockOrderService = stockOrderService;
        this.stockOrderMapper = stockOrderMapper;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestParam Long carVersionId) {
        Long clientId = currentUserService.getCurrentUserId();
        Long id = stockOrderService.create(clientId, carVersionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOrderOutputDto> findById(@PathVariable("id") Long id) {
        return stockOrderService.findById(id)
                .map(stockOrderMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-client/{client-id}")
    public ResponseEntity<List<StockOrderOutputDto>> findByClientId(@PathVariable("client-id") Long clientId) {
        List<StockOrderOutputDto> result = stockOrderService.findByClientId(clientId)
                .stream()
                .map(stockOrderMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<StockOrderOutputDto>> findAll() {
        List<StockOrderOutputDto> result = stockOrderService.findAll()
                .stream()
                .map(stockOrderMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable("id") Long id) {
        stockOrderService.approveOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> pay(@PathVariable("id") Long id) {
        stockOrderService.payOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable("id") Long id) {
        stockOrderService.completeOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        stockOrderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(Long id) {
        stockOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}