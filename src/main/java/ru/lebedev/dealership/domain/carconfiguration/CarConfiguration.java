package ru.lebedev.dealership.domain.carconfiguration;

import java.util.Set;

public record CarConfiguration(
        Long configurationId,
        Set<Long> detailsIds
) {
}