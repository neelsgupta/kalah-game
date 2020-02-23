#Kalah-Game-Application

## Description
 The general rules of the game are explained on Wikipedia: https://en.wikipedia.org/wiki/Kalah. Each of the two players has ​**​six pits​** in front of him/her. To the right of the six pits, each player has a larger pit, his Kalah or house. At the start of the game, six stones are put in each pit. 
 
###Rules 
Game Play
- The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. This can be repeated several times before it's the other player's turn. 
 
Capturing Stones
- During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own (big or little?) pit. 
 
The Game Ends
- The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit. The winner of the game is the player who has the most stones in his big pit. 

## Design
The application is splitted into two seperate sections :
- kalah-game-backend (server): Is a java based sprint boot Restful webservice. Which has API to create, get and play the game.
- kalah-game-frontend (client): Is a angular based front-end app which uses backend server API to display the game.
