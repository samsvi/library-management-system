# Library Management System

### Project Description
This project is a simple library management application built with the Spring Boot framework. It provides a REST API and a web interface using Thymeleaf. Data is stored in a PostgreSQL database running inside a Docker container. The REST API is documented with OpenAPI Swagger UI.

---

### Repository Structure
- `src/main/java` – source code of the Spring Boot application (REST API + Thymeleaf)
- `src/main/resources/templates` – Thymeleaf HTML templates

---

### Installation and Running

Make sure you have Docker and Docker Compose installed.

```bash
git clone https://github.com/samsvi/library-management-system.git
cd library-management-system
docker-compose up --build
```

After startup, the application will be available at:

Web interface (Thymeleaf): http://localhost:8080/books

Swagger UI (API documentation): http://localhost:8080/swagger-ui/index.html

Rest API: http://localhost:8080/api/books (other endpoints are available in Swagger UI)

### Continuous Integration
The repository includes a ci.yml file which configures GitHub Actions to automatically run tests on every push to the repository.

### Architecture
The project uses a Layered Architecture to separate the presentation layer, application logic, and database layer. This approach improves code clarity and maintainability.

### Bonuses
- I implemented pagination and sorting of books in the REST API.
- I added Thymeleaf templates for the web interface. (I did it for the first time, so it may not be perfect.)

