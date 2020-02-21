import { Component, OnInit } from '@angular/core';
import { CommunicationService } from '../communication.service';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-init',
  templateUrl: './game-init.component.html',
  styleUrls: ['./game-init.component.css']
})
export class GameInitComponent implements OnInit {

  errorMessage:string="";
  constructor(private commService:CommunicationService, private router:Router) { }

  ngOnInit() {
  }

  initNewGame() {
    this.commService.initializeNewGame().subscribe(
      res=> {
        this.commService.setGameId(res.id);
        this.router.navigateByUrl("/gameInit/" + res.id);
      }, err=> {
        this.errorMessage = err;
      }
    );
  }

}
