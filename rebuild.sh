#!/bin/bash

echo "Building application..."
./mvnw clean package -DskipTests

echo "Stopping project containers..."
docker-compose -f docker-compose.yml down
docker-compose -f docker-compose-sonar.yml down

echo "Rebuilding and starting containers..."
docker-compose -f docker-compose.yml up --build -d
docker-compose -f docker-compose-sonar.yml up -d

echo "Showing application logs..."
docker-compose logs -f user-management-api 