# RidePilot

This project is a RESTful API built with Spring Boot to manage a taxi service. It provides functionalities for managing reservations, drivers, vehicles, and analytics.

## Features

- **Reservation Management**: Create, update, delete, and view reservations with pricing and availability checks.
- **Driver Management**: Manage driver details, availability, and assignments.
- **Vehicle Management**: Track vehicle details, availability, mileage, and pricing by type.
- **Analytics**:
  - Reservation metrics (average price per km, distance averages, peak booking times, popular zones).
  - Driver analytics (occupancy rate, availability times, status breakdown).
  - Vehicle analytics (average mileage, usage rate, fleet status).

## Project Structure

- **Controller Layer**: Exposes REST endpoints for managing reservations, drivers, and vehicles.
- **Service Layer**: Contains business logic for handling requests and ensuring data consistency.
- **Repository Layer**: Interfaces for database access using Spring Data JPA.
- **DAO Layer**: Handles complex data queries for analytics.
- **Other Components**:
  - **DTO**: Data Transfer Objects for exchanging data between layers.
  - **Mapper**: Converts entities to DTOs.
  - **Exception Handling**: Centralized handling for API errors.
  - **Validation**: Enforces business rules.

## Environment Configuration

This project supports three environments (development, QA, and production), each with its specific configurations. Spring profiles are used to load the appropriate configuration files:

- **Development**: `application-dev.yaml` (H2 database for local testing).
- **QA**: `application-qa.yaml` (MySQL database for staging).
- **Production**: `application-prod.yaml` (PostgreSQL database for live deployment).

### Setting Up Profiles

To set the active profile, you can use the `SPRING_PROFILES_ACTIVE` environment variable or specify it as an argument when running the application:

```bash
# For development
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# For QA
./mvnw spring-boot:run -Dspring-boot.run.profiles=qa

# For production
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
