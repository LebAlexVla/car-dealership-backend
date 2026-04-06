package ru.lebedev.dealership.controller.detail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.application.services.DetailService;
import ru.lebedev.dealership.controller.detail.models.*;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/detail")
public class DetailController {
    private final DetailService detailService;
    private final DetailMapper detailMapper;
    private final DetailFilterMapper detailFilterMapper;

    public DetailController(DetailService detailService, DetailMapper detailMapper, DetailFilterMapper detailFilterMapper) {
        this.detailService = detailService;
        this.detailMapper = detailMapper;
        this.detailFilterMapper = detailFilterMapper;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DetailInputDto detailInputDto) {
        Detail detail = detailMapper.toEntity(detailInputDto);
        Long id = detailService.create(detail, detailInputDto.compatibleCars());

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailOutputDto> findById(@PathVariable Long id) {
        return detailService.findById(id)
                .map(detailMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DetailOutputDto>> find(@ModelAttribute DetailFilterDto filterDto) {
        DetailFilter filter = detailFilterMapper.map(filterDto);
        List<DetailOutputDto> result = detailService.find(filter)
                .stream()
                .map(detailMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}