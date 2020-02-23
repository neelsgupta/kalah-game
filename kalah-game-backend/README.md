# Kalah Game Java Restful Webservice 

Installation
============

### Prerequisites

 - JDK 1.8
 - Apache Maven

### Build
- Build all the artifacts and install it by running the following command `mvn clean install`
- To run test cases `mvn test`
   
### Getting Started
- To start application use command inside game.kalah directory: `mvn spring-boot:run`
- application runs on url : *http://localhost:9090/*
- To start the game(create game) : `curl --header "Content-Type: application/json" --request POST http://<host>:<port>/games`
- To get the created game(get game) : `curl --header "Content-Type: application/json" --request GET http://<host>:<port>/games/{gameId}`
- To make a move(play game) : `curl --header "Content-Type: application/json" --request PUT http://<host>:<port>/games/{gameId}/pits/{pitId}`

### API documentation
- After running the project browse Swagger URL : http://localhost:9090/swagger-ui.html#/ 

In above host and port refer to below
- host : localhost
- port : 9090 (It cab be changed from application.properties file under src/main/resource folder)

### Authors
- Nilesh Gupta

