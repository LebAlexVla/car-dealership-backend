--liquibase formatted sql

--changeset lebedev:006-create-detail-compatible-car-versions
CREATE TABLE detail_compatible_car_versions
(
    detail_id      BIGINT NOT NULL,
    car_version_id BIGINT NOT NULL,

    PRIMARY KEY (detail_id, car_version_id),

    CONSTRAINT fk_detail_car_versions_detail
        FOREIGN KEY (detail_id) REFERENCES detail (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_detail_car_versions_car_version
        FOREIGN KEY (car_version_id) REFERENCES car_version (id)
            ON DELETE CASCADE
);

--rollback DROP TABLE detail_compatible_car_versions;