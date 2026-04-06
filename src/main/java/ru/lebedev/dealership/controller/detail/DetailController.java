package ru.lebedev.dealership.controller.detail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebedev.dealership.application.filters.DetailFilter;
import ru.lebedev.dealership.application.services.DetailService;
import ru.lebedev.dealership.controller.detail.dto.DetailFilterDto;
import ru.lebedev.dealership.controller.detail.dto.DetailInputDto;
import ru.lebedev.dealership.controller.detail.dto.DetailOutputDto;
import ru.lebedev.dealership.controller.detail.mapper.DetailMapper;
import ru.lebedev.dealership.domain.detail.Detail;

import java.util.List;

@RestController
@RequestMapping("/detail")
public class DetailController {
    private final DetailService detailService;
    private final DetailMapper detailMapper;

    public DetailController(DetailService detailService, DetailMapper detailMapper) {
        this.detailService = detailService;
        this.detailMapper = detailMapper;
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
        DetailFilter filter = detailMapper.toFilter(filterDto);
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