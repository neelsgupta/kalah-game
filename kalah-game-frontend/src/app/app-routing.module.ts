import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GameInitComponent } from './new-game-init/game-init.component';
import { GameScoreComponent } from './game-score/game-score.component';

const routes: Routes = [
  { path: 'gameInit', component: GameInitComponent},
  { path: 'gameInit/:id', component: GameScoreComponent},
  { path: '',redirectTo: '/gameInit',pathMatch: 'full'},
  // otherwise redirect to game
  { path: '**', redirectTo: 'GameInitComponent' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
