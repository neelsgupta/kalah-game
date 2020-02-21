#Kalah-Game-Application

## Description
 The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah. Each of the two players has ​**​six pits​** ​in front of him/her. To the right of the six pits, each player has a larger pit, his Kalah or house. At the start of the game, six stones are put in each pit. 
The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other player's turn. 
When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the other players' pit) and puts them in his own Kalah. 
The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah. 

Installation
============

### Prerequisites

 - JDK 1.8
 - Apache Maven

### Build

- Build all the artifacts and install it by running the following command `mvn clean install`
   
### Getting Started
- To start application use command inside game.kalah directory: `mvn spring-boot:run`
- application runs on url : *http://localhost:9090/*
- To start the game(create game) : `curl --header "Content-Type: application/json" --request POST http://<host>:<port>/games`
- To get the created game(get game) : `curl --header "Content-Type: application/json" --request GET http://<host>:<port>/games/{gameId}`
- To make a move(play game) : `curl --header "Content-Type: application/json" --request PUT http://<host>:<port>/games/{gameId}/pits/{pitId}`

Swagger can be access
- Swagger URL : http://localhost:9090/swagger-ui.html#/ 

In above host and port refer to below
- host : localhost
- port : 9090 (It cab be changed from application.properties file under src/main/resource folder)

