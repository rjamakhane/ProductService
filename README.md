# Product Service

## Overview
This project is a Spring Boot application that serves as a Product Service. It uses MySQL for the database and Redis for caching. The application is also registered with a Eureka server for service discovery.

## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL
- Redis
- Eureka Server

## Configuration
The application configuration is managed through the `application.properties` file located in `src/main/resources/`. Below are the key properties:

### Database Configuration
```ini
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/productDB
spring.datasource.username=${DM_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Redis Configuration
```ini
spring.data.redis.host=${REDIS_HOST}
spring.data.redis.username=${REDIS_USERNAME}
spring.data.redis.password=${REDIS_PASSWORD}
spring.data.redis.port=${REDIS_PORT}
```

### Eureka Client Configuration
```ini
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Application Configuration
```ini
spring.application.name=ProductService
server.port=${SERVER_PORT}
logging.level.org.springframework=TRACE
```

## Running the Application
1. Ensure MySQL and Redis are running.
2. Configure the environment variables for database and Redis connection.
3. Start the Eureka server.
4. Build the project using Maven:
   ```sh
   mvn clean install
   ```
5. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## Endpoints
The application exposes various REST endpoints for managing products. Detailed API documentation can be found in the `src/main/resources/static` directory.

## License
This project is licensed under the MIT License.