# acook-users-api

A Spring Boot REST API for managing users, backed by an H2 file database with Flyway migrations.

## Prerequisites

- Java 25
- (Optional) A separate frontend running at `http://localhost:5173` — CORS is configured for this origin

## Build

From the project root:

```bash
./mvnw clean install
```

## Run

```bash
./mvnw spring-boot:run
```

(On Windows: `mvnw.cmd spring-boot:run`)

The app starts on **http://localhost:8080**. On first run, Flyway automatically creates the schema and seeds it with data from `UserInformation.csv`.
