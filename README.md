# Delivery Management Service

A Service to handle delivery order management and tracking.

## Technology Stack

- Java JDK 17
- Spring Boot 3.1.0
- Maven 3.8.x
- Docker
- MySQL

## API Endpoints

### Order Management
- POST `/api/v1/orders` - Creates a new order
- GET `/api/v1/orders/all?page=1&size=55` - Get all orders with pagination
- GET `/api/v1/orders?status={status}` - Returns orders on the basis of status with pagination
- GET `/api/v1/customers/top` - Returns top 3 customers with highest delivered orders
- GET `/api/v1/orders/status-count` - Returns the count of orders on the basis of status
- GET `/actuator` - To check the health and metrics of the application

## Assumptions
- Considering the customer name is unique

## Build & Run
- git clone https://github.com/Tr4ce007/delivery-management-service
- cd delivery-management-service
- Go to application.properties and update the datasource to this line to use local mysql
```spring.datasource.url=jdbc:mysql://host.docker.internal:3306/deliverymgmt```
- Update mysql username and password if required
- mvn clean package -DskipTests
- Once the build is successful, change the name of the .jar file in target folder one matching within the dockerfile ```deliverymgmt-1```
- docker build -t deliverymgmt-app .
- docker run -d --name deliverymgmt-app -p 8080:8080 deliverymgmt-app
- Go to http://localhost:8080/actuator after starting the application (wait for a few seconds)
- Make sure the mysql service is running on port 3306 on your local machine and credentials are valid

## Concurrency, Multithreaded Environment and Optimizations
- The application includes a scheduler to simulate real-world scenarios by transitioning orders from "pending" to "in progress" and finally to "delivered."
- The application uses Executor service to handle multiple thread to update status of the order.
- The application implements pagination to handle listing of orders in all orders.
- The application uses custom exception handling to handle exceptions and return appropriate response.
- The application implements a pagination feature to return a limited number of orders by status.
- The application has a endpoint to check the health and metrics of the application.
