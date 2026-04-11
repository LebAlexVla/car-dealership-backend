package ru.lebedev.dealership.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.lebedev.dealership.domain.detail.Detail;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long>, JpaSpecificationExecutor<Detail> {
}