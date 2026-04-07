# Jordan's Gamer Haven Inventory

A Spring Boot inventory management app for a small gaming shop. The app tracks parts and product packages, supports in-house and outsourced part types, associates parts with products, and enforces inventory rules around stock levels and product purchasing.

## What It Demonstrates

- Spring MVC controllers serving a Thymeleaf web interface
- Spring Data JPA repositories and service-layer abstractions
- H2 file database persistence for local development
- Domain modeling for products, parts, in-house parts, and outsourced parts
- Form validation for inventory constraints and product pricing
- Many-to-many part/product association management
- Inventory decrement logic when a product is purchased
- JUnit tests for domain objects, repositories, and services

## Features

- Browse and search parts and product packages
- Add, update, and delete in-house or outsourced parts
- Add, update, and delete product packages
- Associate and remove parts from products
- Buy products and decrement product inventory
- Validate part inventory against minimum and maximum values
- Prevent product inventory increases that would drop associated parts below their minimum stock
- Seed a starter inventory for local development
- Inspect local data through the H2 console

## Tech Stack

- Java 17
- Spring Boot 2.6.6
- Spring MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- Maven Wrapper
- JUnit 5

## Run Locally

Prerequisites:

- Java 17+
- `JAVA_HOME` configured

From the repository root:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Then open:

```text
http://localhost:8080/
```

The app redirects to the main inventory screen at:

```text
http://localhost:8080/mainscreen
```

H2 console:

```text
http://localhost:8080/h2-console
```

Default JDBC URL:

```text
jdbc:h2:file:./data/gamer_inventory
```

Use username `sa` and a blank password.

## Test And Verify

Run tests from the repository root:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

Manual smoke test checklist:

- Open `http://localhost:8080/` and confirm the inventory UI loads.
- Search parts and products from the main screen.
- Add an in-house part and an outsourced part.
- Create a product and associate parts with it.
- Buy a product and confirm product inventory decreases.
- Try an invalid inventory value and confirm validation prevents the save.
- Open the H2 console and inspect the seeded inventory tables.

## Notes

Local H2 database files and generated IDE exports are intentionally ignored so the repository stays lightweight and reproducible.

## Resume Bullet

Built a Spring Boot inventory management web app with Thymeleaf, Spring Data JPA, H2 persistence, searchable inventory screens, part/product association workflows, purchase-driven inventory updates, and validation rules for min/max stock constraints.
