# Spring Boot API Setup Guide

## 1. Prerequisites
- Docker and Docker Compose installed
- Git installed

## 2. Quick Start

### Clone and Run
```bash
# Clone the repository
git clone https://github.com/yourusername/spring-ai-demo.git
cd spring-ai-demo

# Start the application
docker-compose up --build
```

The API will be running at: http://localhost:8080

### Basic Test
```bash
# Create a test user
curl -X POST "http://localhost:8080/api/v1/users" \
-H "Content-Type: application/json" \
-d '{
    "name": "Test User",
    "email": "test@example.com",
    "phoneNumber": "+1234567890"
}'

# Get all users
curl "http://localhost:8080/api/v1/users"
```

## 3. Common Commands

### Docker Operations
```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f

# Restart services
docker-compose restart
```

### Cleanup
```bash
# Remove all containers and volumes
docker-compose down -v

# Clean and rebuild
docker-compose down -v
docker system prune -a
docker-compose up --build
```

## 4. Troubleshooting

If the application fails to start:
```bash
# Check container status
docker-compose ps

# Check application logs
docker-compose logs spring-app

# Check database logs
docker-compose logs db
```

## 5. Database Access
```bash
# Connect to database
docker-compose exec db psql -U your_db_user -d your_db_name
```

## 6. Postman Collection

### Postman Collection JSON
```json
{
  "info": {
    "name": "Enhanced User API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get All Users (Paginated)",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/v1/users?page=0&size=20&sort=name,asc",
          "query": [
            {"key": "page", "value": "0"},
            {"key": "size", "value": "20"},
            {"key": "sort", "value": "name,asc"}
          ]
        }
      }
    },
    {
      "name": "Create User",
      "request": {
        "method": "POST",
        "header": [
          {"key": "Content-Type", "value": "application/json"}
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"name\": \"John Doe\",\n    \"email\": \"john@example.com\",\n    \"phoneNumber\": \"+1234567890\"\n}"
        },
        "url": "http://localhost:8080/api/v1/users"
      }
    },
    {
      "name": "Get User by ID",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/v1/users/1"
      }
    },
    {
      "name": "Update User",
      "request": {
        "method": "PUT",
        "header": [
          {"key": "Content-Type", "value": "application/json"}
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"name\": \"John Updated\",\n    \"email\": \"john.updated@example.com\",\n    \"phoneNumber\": \"+1987654321\",\n    \"status\": \"ACTIVE\"\n}"
        },
        "url": "http://localhost:8080/api/v1/users/1"
      }
    },
    {
      "name": "Delete User",
      "request": {
        "method": "DELETE",
        "url": "http://localhost:8080/api/v1/users/1"
      }
    },
    {
      "name": "Search Users by Name",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/v1/users?name=john&page=0&size=20",
          "query": [
            {"key": "name", "value": "john"},
            {"key": "page", "value": "0"},
            {"key": "size", "value": "20"}
          ]
        }
      }
    }
  ]
}
```

This enhanced version provides a more complete and professional REST API implementation. Let me know if you need any clarification or have questions about specific features! 