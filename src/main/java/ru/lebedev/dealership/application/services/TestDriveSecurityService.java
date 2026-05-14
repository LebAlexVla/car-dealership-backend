package ru.lebedev.dealership.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebedev.dealership.infrastructure.persistence.repository.TestDriveRepository;

@Service("testDriveSecurityService")
public class TestDriveSecurityService {
    private final TestDriveRepository testDriveRepository;
    private final CurrentUserService currentUserService;

    public TestDriveSecurityService(
            TestDriveRepository testDriveRepository,
            CurrentUserService currentUserService
    ) {
        this.testDriveRepository = testDriveRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional(readOnly = true)
    public boolean isTestDriveOwner(Long testDriveId) {
        Long currentUserId = currentUserService.getCurrentUserId();

        return testDriveRepository.findById(testDriveId)
                .map(testDrive -> testDrive.getClient().getId().equals(currentUserId))
                .orElse(false);
    }
}