--liquibase formatted sql

--changeset lebedev:004-create-car-version-colors
CREATE TABLE car_version_colors
(
    car_version_id BIGINT      NOT NULL,
    color          VARCHAR(20) NOT NULL,

    PRIMARY KEY (car_version_id, color),

    CONSTRAINT fk_car_version_colors_car_version
        FOREIGN KEY (car_version_id) REFERENCES car_version (id)
            ON DELETE CASCADE
);

--rollback DROP TABLE car_version_colors;