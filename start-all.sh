#!/bin/bash

# Configuration
LOG_DIR="./logs"
mkdir -p "$LOG_DIR"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}=== FlowSync Microservices Startup ===${NC}"

# Check for .env file
if [ -f .env ]; then
    echo -e "${GREEN}Loading environment variables from .env...${NC}"
    set -a
    source .env
    set +a
else
    echo -e "${YELLOW}Warning: .env file not found. Ensure required environment variables (JWT_SECRET_KEY, DB_USER_NAME, DB_USER_PASSWORD) are set in your shell.${NC}"
fi

# Check for PostgreSQL (on port 5433 as per application.properties)
echo -e "${BLUE}Checking PostgreSQL dependency on port 5433...${NC}"
nc -z localhost 5433 > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo -e "${RED}Error: PostgreSQL is not reachable on localhost:5433. Please start your database first.${NC}"
    exit 1
fi
echo -e "${GREEN}PostgreSQL is up!${NC}"

# Stop any existing Spring Boot services
echo -e "${BLUE}Stopping any existing Spring Boot services...${NC}"
pkill -f spring-boot 2>/dev/null
pkill -f "java -jar" 2>/dev/null
sleep 2

# Function to start a service and wait for it
start_service() {
    local service_name=$1
    local dir_name=$2
    local port=$3
    local log_file="$LOG_DIR/${service_name}.log"

    echo -e "${YELLOW}Starting ${service_name} on port ${port}...${NC}"
    
    (cd "$dir_name" && mvn spring-boot:run > "../$log_file" 2>&1 &)
    
    # Wait for the port to become active
    local count=0
    local max_retries=60
    while ! nc -z localhost "$port" > /dev/null 2>&1; do
        sleep 1
        ((count++))
        if [ $count -ge $max_retries ]; then
            echo -e "${RED}Error: ${service_name} failed to start within ${max_retries} seconds. Check $log_file for details.${NC}"
            exit 1
        fi
    done
    
    echo -e "${GREEN}${service_name} is running!${NC}"
}

# Start Services in Order
echo ""
start_service "Eureka Server" "eurekaServer" 8761
sleep 5 # Give registry a moment

start_service "Auth Service" "authService" 8083
start_service "User Service" "userService" 8082
start_service "API Gateway" "apiGateway" 8080

echo -e "\n${GREEN}🚀 All services started successfully!${NC}"
echo -e "${BLUE}Eureka Dashboard: http://localhost:8761${NC}"
echo -e "${BLUE}API Gateway:      http://localhost:8080${NC}"
echo -e "${BLUE}ZIPKIN : http://localhost:9411${NC}"
echo -e "${BLUE}POSTGRESS : http://localhost:5050${NC}"
echo -e "${YELLOW}Logs are available in the $LOG_DIR directory.${NC}"

analyse this whole file review + explanin
