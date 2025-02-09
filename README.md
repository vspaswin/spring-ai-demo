# Spring AI Demo API

A comprehensive RESTful API built with Spring Boot demonstrating user management functionality with HATEOAS, validation, and Docker support.

## Features

- CRUD operations for User management
- Input validation and error handling
- Pagination and filtering support
- HATEOAS compliant responses
- Docker containerization
- PostgreSQL database
- Auditing with creation and update timestamps

## Technologies Used

- Spring Boot 3.x
- Spring Data JPA
- Spring HATEOAS
- PostgreSQL
- Docker & Docker Compose
- Maven
- Java 17

## Getting Started

### Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven (optional if using mvnw)

### Quick Start

1. Clone the repository:
```bash
git clone https://github.com/vspaswin/spring-ai-demo.git
cd spring-ai-demo
```

2. Start the application using Docker:
```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`

## API Endpoints

### User Management

- `GET /api/v1/users` - Get all users (paginated)
  - Query params: page, size, name, status
- `POST /api/v1/users` - Create a new user
- `GET /api/v1/users/{id}` - Get user by ID
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user

### Example Requests

```bash
# Create a user
curl -X POST "http://localhost:8080/api/v1/users" \
-H "Content-Type: application/json" \
-d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phoneNumber": "+1234567890"
}'

# Get users with pagination
curl "http://localhost:8080/api/v1/users?page=0&size=20"
```

## Project Structure

```
spring-ai-demo/
├── src/main/java/
│   └── spring/ai/example/spring_ai_demo/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── model/
│       └── repository/
├── src/main/resources/
│   └── application.properties
├── docker-compose.yml
├── Dockerfile
├── SETUP.md
├── COMMANDS.md
└── GIT_COMMANDS.md
```

## Documentation

- `SETUP.md` - Detailed setup instructions
- `COMMANDS.md` - API and Docker commands reference
- `GIT_COMMANDS.md` - Git operations guide

## Development

To run the application locally without Docker:

1. Update application.properties with local PostgreSQL settings
2. Run the application:
```bash
./mvnw spring-boot:run
```

### Testing

```bash
./mvnw test
```

## Docker Commands

Start the application:
```bash
docker-compose up --build
```

View logs:
```bash
docker-compose logs -f
```

Stop the application:
```bash
docker-compose down
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For support, please open an issue in the GitHub repository.

## SonarQube

To run SonarQube analysis:

1. Start SonarQube container:

```bash
docker-compose -f docker-compose-sonar.yml up -d
```

2. Run SonarQube analysis:

```bash
./mvnw sonar:sonar
```

3. Access SonarQube at `http://localhost:9000`

4. Generate a token in SonarQube:

```bash
docker exec -it sonarqube sonar-scanner -Dsonar.login=<your_token>
```

5. Run SonarQube analysis:

```bash
./mvnw sonar:sonar
```

## SonarQube Analysis

### Local Setup

1. Start SonarQube:
```bash
# Start SonarQube and PostgreSQL
docker-compose -f docker-compose-sonar.yml up -d

# Wait for SonarQube to start (usually takes 1-2 minutes)
```

2. Access SonarQube:
   - URL: http://localhost:9000
   - Default credentials: admin/admin
   - Change password when prompted

3. Generate Token:
   - Go to User → My Account → Security
   - Generate new token
   - Save token in `.env` file

4. Run Analysis:
```bash
# Using env file
export $(cat .env | xargs) && mvn clean verify sonar:sonar

# Or Windows PowerShell
Get-Content .env | ForEach-Object { $env:$($_.Split('=')[0])=$_.Split('=')[1] }; mvn clean verify sonar:sonar
```

5. View Reports:
   - Go to http://localhost:9000/projects
   - Select your project
   - View Code Coverage, Code Smells, Bugs, etc.

### Cleanup
```bash
# Stop SonarQube
docker-compose -f docker-compose-sonar.yml down -v
```

## SonarQube Analysis

To run SonarQube analysis:

1. Start SonarQube container:

```bash
docker-compose -f docker-compose-sonar.yml up -d
```

2. Run SonarQube analysis:

```bash
./mvnw sonar:sonar
```

3. Access SonarQube at `http://localhost:9000`

4. Generate a token in SonarQube:

```bash
docker exec -it sonarqube sonar-scanner -Dsonar.login=<your_token>
```

5. Run SonarQube analysis:

```bash
./mvnw sonar:sonar
```

## SonarQube Analysis

To run SonarQube analysis:

1. Start SonarQube container:

```bash
docker-compose -f docker-compose-sonar.yml up -d
```

2. Run SonarQube analysis:

    