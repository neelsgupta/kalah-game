import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { PitScoreResponse } from './models/pit-score-response.model';

@Injectable()
export class CommunicationService {
  gameId: string = "";
  configUrl = "http://localhost:9090";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept':  'application/json'
    })
  }

  initializeNewGame() {
    return this.http.post<PitScoreResponse>(this.configUrl + '/games/', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
      //return this.http.get<any>("assets/mockJson/init-game.json");
  }

  initGameScore() {
    return this.http.get<PitScoreResponse>(this.configUrl + '/games/' + this.gameId, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )
    // return this.http.get<any>("assets/mockJson/init-score.json");
  }

  getGameScoreDetails(pitId) {
    return this.http.put<PitScoreResponse>(this.configUrl + '/games/' + this.gameId + '/pits/' + pitId, this.httpOptions)
      .pipe(
        retry(1),
        catchError(e => this.handleError(e))
      )
      //  return this.http.get<any>("assets/mockJson/updated-score.json");
  }

  setGameId(newGameId) {
    this.gameId = newGameId;
  }

  getGameId() {
    return this.gameId;
  }

  handleError(err) {
    let errorMessage = '';
    if (err.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = err.error.message;
    } else {
      // Get server-side error
      errorMessage = `${err.error.message} =${err.error.id}`;
    }
    return throwError(errorMessage);
  }
}