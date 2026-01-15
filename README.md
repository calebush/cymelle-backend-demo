# Cymelle-backend-demo

A Spring Boot based backend providing Authentication, Product Management, Order and Ride services.

## Prerequisites

* IDE i.e Intellij
* **Java 21 SDK**
* **MySQL Server 8.0+**
* **Maven 3.9+** (or use the provided `./mvnw` wrapper)


## 1. Database Setup

1. Log into your MySQL instance:
   ```sql
   CREATE DATABASE cymelle;
   ```
2. The tables will be automatically created by the application Flyway migrations upon the first run, provided the credentials are correct.

## 2. Configuration

Open `src/main/resources/application.yml` and update your database credentials:

```properties
    username: YOUR_USERNAME
    password: YOUR_PASSWORD
```
3. Build and Run

./mvnw clean install

./mvnw spring-boot:run

### Access Swagger api at ```http://localhost:8081/swagger-ui/index.html```

### Initial user is email: admin@cymelle.com password: admin123
