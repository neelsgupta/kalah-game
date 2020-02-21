import { Component, OnInit } from '@angular/core';
import { CommunicationService } from '../communication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-score',
  templateUrl: './game-score.component.html',
  styleUrls: ['./game-score.component.css']
})
export class GameScoreComponent implements OnInit {

  errorMsg="";
  board= {};
  playerName:string="";
  nextPlayer:string="";
  gameStatus:string="";

  constructor(private commService:CommunicationService, private router:Router) {}

  ngOnInit() {
    this.initScore();
  }

  initScore() {
    this.commService.initGameScore().subscribe(
      res=> {
        this.nextPlayer= res.nextPlayer;
        this.playerName = this.nextPlayer=="1"? 'Player1':'Player2';
        this.board= res.score;
        this.gameStatus= res.gameStatus;
      }, err=> {

      }
    );
  }

  nextMoveByPit(pitNumber:Number) {
    console.log(pitNumber);
    this.commService.getGameScoreDetails(pitNumber).subscribe(
      res=> {
        this.nextPlayer = res.nextPlayer;
        this.playerName =  this.nextPlayer=="1"? 'Player1':'Player2';
        this.board= res.score;
        this.gameStatus = res.gameStatus;
          if(this.gameStatus!="IN_PROGRESS") {
            this.nextPlayer = "";
          }
          this.errorMsg="";
      }, err=> {
        this.errorMsg=err;
      }
    );
  }

  resetGame() {
    this.commService.setGameId("");
    this.initNewGame();
  }

  initNewGame() {
    this.commService.initializeNewGame().subscribe(
      res=> {
        this.commService.setGameId(res.id);
        this.initScore();
      }, err=> {

      }
    );
  }

}
