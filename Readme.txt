docker run -d deliverymgmt --network host -e spring.application.name=deliverymgmt -e spring.datasource.url=jdbc:mysql://host.docker.internal:3306/deliverymgmt -e spring.jpa.hibernate.ddl-auto=update -e spring.datasource.username=root -e spring.datasource.password=root -e management.endpoints.web.exposure.include=health,metrics -p 8080:8080 deliverymgmt

spring.datasource.url=jdbc:mysql://host.docker.internal:3306/deliverymgmt
// ^ when running in docker

// Command to build

mvn clean package -DskipTests


// Docker Commands

docker build -t deliverymgmt-app .		// where dockerfile is 

docker run -d --name deliverymgmt-app -p 8080:8080 deliverymgmt-app
