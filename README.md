# Dealership Backend

Backend-сервис для информационной системы мультибрендового автосалона. Проект реализует каталог автомобилей, конфигуратор комплектаций, заказы, тест-драйвы, REST API, хранение данных в PostgreSQL и ролевую авторизацию через Keycloak.

## Стек

Java 21, Spring Boot, Spring Web, Spring Data JPA, Spring Security, OAuth2 Resource Server, Keycloak, PostgreSQL, Liquibase, MapStruct, Gradle, JUnit 5, Mockito, AssertJ, Testcontainers, Docker.

## Что реализовано

- каталог автомобилей: модели, версии, характеристики, цены;
- фильтрация автомобилей по параметрам;
- управление комплектующими и проверка совместимости деталей с автомобилями;
- конфигуратор автомобиля с базовой и пользовательской комплектацией;
- заказы автомобилей из наличия и автомобилей с индивидуальной конфигурацией;
- жизненный цикл заказов со статусами и доменными ограничениями;
- заявки на тест-драйв;
- REST API с DTO и MapStruct-мапперами;
- миграции БД через Liquibase;
- авторизация через Spring Security и Keycloak;
- разграничение доступа по ролям `USER`, `MANAGER`, `WAREHOUSE_ADMIN`, `ADMIN`;
- проверки владельца ресурса на уровне сервисного слоя;
- unit- и integration-тесты с PostgreSQL в Testcontainers.

## Архитектура

Проект построен по многослойной архитектуре:

```text
controller      REST API, DTO, обработка HTTP-запросов
application     сервисы, use cases, проверки доступа
domain          бизнес-сущности, value objects, доменные правила
infrastructure  JPA-репозитории, Specifications, Security, интеграции
```

Контроллеры не возвращают JPA-сущности наружу. Вся работа с API выполняется через DTO, а бизнес-правила вынесены в domain/application-слои.

## Основные доменные сущности

- `CarHead` — модель автомобиля;
- `CarVersion` — конкретная версия автомобиля;
- `Detail` — комплектующая;
- `CarConfiguration` — итоговая конфигурация автомобиля;
- `StockOrder` — заказ автомобиля из наличия;
- `ConfiguredCarOrder` — заказ автомобиля с индивидуальной комплектацией;
- `TestDrive` — заявка на тест-драйв;
- `User` — пользователь системы.

## Безопасность

Приложение работает как OAuth2 Resource Server и проверяет JWT-токены Keycloak.

Роли:

- `USER` — создаёт заказы, работает только со своими заказами и заявками;
- `MANAGER` — просматривает и обрабатывает заказы клиентов;
- `WAREHOUSE_ADMIN` — выполняет складские операции;
- `ADMIN` — имеет полный доступ к системе.

Для операций с пользовательскими ресурсами реализованы owner-checks через `@PreAuthorize` и сервисы безопасности.

## Запуск

Требования:

- Java 21;
- Docker;
- PostgreSQL;
- Keycloak.

Запуск приложения:

```bash
./gradlew bootRun
```

Запуск тестов:

```bash
./gradlew test
```

Если в проекте настроены интеграционные тесты отдельной задачей:

```bash
./gradlew integrationTest
```

Swagger UI после запуска:

```text
/swagger-ui/index.html
```

## Статус

Проект содержит готовую backend-основу автосалона: доменную модель, REST API, PostgreSQL persistence layer, миграции, Keycloak/Spring Security, ролевую модель доступа и тесты.
