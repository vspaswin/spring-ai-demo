# Spring AI Demo API

A RESTful API built with Spring Boot demonstrating user management functionality.

## Technologies Used

- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose

## Getting Started

### Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven (optional if using mvnw)

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/spring-ai-demo.git
   cd spring-ai-demo
   ```

2. Start the application using Docker Compose:

   ```bash
   docker-compose up --build
   ```

The API will be available at `http://localhost:8080`

### API Endpoints

- `GET /api/v1/users` - Get all users (paginated)
- `POST /api/v1/users` - Create a new user
- `GET /api/v1/users/{id}` - Get user by ID
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user

## Development

To run the application locally without Docker:

1. Update application.properties with local PostgreSQL settings
2. Run the application:
```bash
./mvnw spring-boot:run
```

## License

This project is licensed under the MIT License - see the LICENSE file for details

