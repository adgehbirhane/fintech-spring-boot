# RESTful API and WebSocket Integration in Spring boot

## Project Overview

This project is a secure RESTful API with WebSocket functionality designed to allow authenticated users to send messages to either all connected users or a specific user.

## Features

- **User Management**: CRUD operations for user data with role-based access.
- **Authentication and Authorization**: JWT-based authentication and role-based access control.
- **WebSocket Messaging**: Real-time communication for both broadcast and direct messages.
- **Pagination**: Supports pagination for large datasets on `GET /api/users`.
- **Validation**: Ensures data integrity with validation rules for user inputs.

## Technologies Used

- **Java** (Latest Stable Version)
- **Spring Boot** for REST API and WebSocket
- **JWT** for secure authentication
- **MySQL** as the relational database
- **Hibernate/JPA** for database access
- **Maven** as the build tool

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- PostgreSQL Database
- Maven

### Database Setup

1. Create a database.
2. Update `application.properties` with your database credentials.

```properties
spring.application.name=fintech
spring.datasource.url=jdbc:postgresql://localhost:5432/fintech_db
spring.datasource.username=fintech_user
spring.datasource.password=Root@123
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
#spring.jpa.hibernate.ddl-auto=create
#spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
#debug=true
jwt.secret="JvbGU05JHiOiK1JTiIs85CaI6MT"
# default to 24 hours if not set
jwt.expirationMs=${JWT_EXPIRATION_MS:86400000}

# Set root level logging to WARN to suppress INFO and DEBUG messages
logging.level.root=WARN

# Turn off logging for specific packages or classes
logging.level.com.bly.fintech.service.AuthService=ERROR
logging.level.org.springframework.security=ERROR

spring.mvc.throwExceptionIfNoHandlerFound=true

# Log to a file (a few logs)
logging.file.name=logger.txt

# Set log level for all classes
logging.level.org.springframework.web.filter=DEBUG

# Set log format for the console (optional)
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Set the level for specific packages (optional)
logging.level.com.bly.fintech=INFO
```
### Running the Project

1. Clone the repository.
   ```bash
   git clone https://github.com/adgehbirhane/fintech-spring-boot.git
    ```
2. Navigate to the project directory and run:
   ```bash
   # Better to use IDE GUI interface to run the program
   mvn spring-boot:run
   

