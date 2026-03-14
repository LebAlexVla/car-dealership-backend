package ru.lebedev.dealership.domain.carconfiguration;

import java.util.Set;

public record CarConfiguration(
        long configurationId,
        Set<Long> detailsIds
) {
}