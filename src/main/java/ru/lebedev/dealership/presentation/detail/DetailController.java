package ru.lebedev.dealership.presentation.detail;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lebedev.dealership.application.services.DetailService;

@RestController
@RequestMapping("/detail")
public class DetailController {
    private final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }
}