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

# API Docs

### Auth - Signup
```HTTPie
POST http://localhost:8080/api/auth/signup
```

```json
{
"username": "user",
"email": "user@gmail.com",
"password": "User@123"
}
```

### Auth - SignIn
```HTTPie
POST http://localhost:8080/api/auth/signIn
```
```json
{
"username": "admin",
"password": "Admin@123"
}
```

### Users - Create User
```HTTPie
POST http://localhost:8080/api/users
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MzEyNTM1OTIsImV4cCI6MTczMTMzOTk5Mn0.g5Rt7wXM1GHztvVK55c74j3b2W12we6H9FsSW-_KTNWkZaPNO0_WKZv_j5XbPHZqzJRy3mg8GMuq40ITvl5qow
```

```json
{
"username": "belay",
"email": "belay@gmail.com",
"password": "Belay@123",
"role": "USER"
}
```

### Users - Find All Users (accessed by admins only)
```HTTPie
GET http://localhost:8080/api/users
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTczMTI1MzczNiwiZXhwIjoxNzMxMzQwMTM2fQ.610WCFCdMcHjqvH81cf0bQqp4XHU5zc6BkHpYBNIdFV0jDJzNPtmKaOl57Ri7WBqXurEVwQN84alFC5kMmVs9A
```
### Users - Find User by ID
```HTTPie
GET http://localhost:8080/api/users/{{userId}}
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTczMTI1MzczNiwiZXhwIjoxNzMxMzQwMTM2fQ.610WCFCdMcHjqvH81cf0bQqp4XHU5zc6BkHpYBNIdFV0jDJzNPtmKaOl57Ri7WBqXurEVwQN84alFC5kMmVs9A
```

### Users - Update User by ID
```HTTPie
PUT http://localhost:8080/api/users/{{userId}}
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTczMTI1MzczNiwiZXhwIjoxNzMxMzQwMTM2fQ.610WCFCdMcHjqvH81cf0bQqp4XHU5zc6BkHpYBNIdFV0jDJzNPtmKaOl57Ri7WBqXurEVwQN84alFC5kMmVs9A
```

```json
{
"username": "belay",
"email": "belay@gmail.com",
"password": "Belay@123",
"role": "USER"
}
```
### Users - Delete User by ID (accessed by admins only)
```HTTPie
DELETE http://localhost:8080/api/users/{{userId}}
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MzEyNTM1OTIsImV4cCI6MTczMTMzOTk5Mn0.g5Rt7wXM1GHztvVK55c74j3b2W12we6H9FsSW-_KTNWkZaPNO0_WKZv_j5XbPHZqzJRy3mg8GMuq40ITvl5qow
```

# WebSocket Docs
### WebSocket - Send Message (Broadcast)
```HTTPie
ws://localhost:8080/ws/notifications
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MzEyNTM1OTIsImV4cCI6MTczMTMzOTk5Mn0.g5Rt7wXM1GHztvVK55c74j3b2W12we6H9FsSW-_KTNWkZaPNO0_WKZv_j5XbPHZqzJRy3mg8GMuq40ITvl5qow
```
```json 
{
"action": "broadcast",
"message": "Hello everyone!"
}
```

### WebSocket - Send Message (private)
```HTTPie
ws://localhost:8080/ws/notifications
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTczMTI1MzczNiwiZXhwIjoxNzMxMzQwMTM2fQ.610WCFCdMcHjqvH81cf0bQqp4XHU5zc6BkHpYBNIdFV0jDJzNPtmKaOl57Ri7WBqXurEVwQN84alFC5kMmVs9A
```
```json 
{
   "action": "privateMessage",
   "targetUser": "admin",
   "message": "Hello, how are you?"
}
```
