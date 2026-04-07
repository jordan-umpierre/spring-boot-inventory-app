# Jordan's Gamer Haven Inventory

A Spring Boot inventory management app for a small gaming shop. The app tracks gamer parts and product packages, supports in-house and outsourced parts, and enforces inventory rules around minimum and maximum stock levels.

## Features

- Browse and search parts and product packages
- Add, update, and delete in-house or outsourced parts
- Add, update, and delete product packages
- Associate parts with products
- Buy products and decrement product inventory
- Validate part inventory against minimum and maximum values
- Seed a starter inventory for local development
- Use an H2 database with the H2 console enabled for local inspection

## Tech Stack

- Java 17
- Spring Boot 2.6.6
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- JUnit 5

## Run Locally

Prerequisite: install a JDK and set `JAVA_HOME`.

```powershell
.\mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

H2 console:

```text
http://localhost:8080/h2-console
```

Default JDBC URL:

```text
jdbc:h2:file:./data/gamer_inventory
```

## Test

```powershell
.\mvnw.cmd test
```

## Notes

Local database files and generated IDE exports are intentionally ignored.
