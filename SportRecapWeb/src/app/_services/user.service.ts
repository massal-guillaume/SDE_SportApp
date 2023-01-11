import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';
import { TokenStorageService } from './token-storage.service';
import { Exercice } from '../component/model/exercice.model';

const API_URL = environment.host;



@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient,private tokenStorage: TokenStorageService,private auth:AuthService)  {


  }


 


  getExercices(): Observable<any> {
    var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getToken()! })}
    return this.http.get( API_URL+"/exercice", httpOptions);
  }

  getExercicesListe(): Observable<any> {
    var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getToken()! })}
    return this.http.get( API_URL+"/liste_exercice", httpOptions);
  }



  postExercices(nom:string,categorie:string,lastvalue:string,histo:Map<string,number>): Observable<any>{
    var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getToken()! })}
    const historique = Object.fromEntries(histo);
    return this.http.post( API_URL+"/exercice",{
      nom,
      categorie,
      lastvalue,
      historique,
    },httpOptions);
  }

  getCategory(): Observable<any>{
    var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getToken()! })}
    return this.http.get( API_URL+"/category", httpOptions);
  }

  addExerciceInUserListe(id:number): Observable<any>{
    var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getToken()! })}
    return this.http.get( API_URL+"/save_exo/"+id, httpOptions);
  }

 saveWeight(id:number,weight:number): Observable<any>{
  var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getToken()! })}
  return this.http.post( API_URL+"/exercice",{
    id,
    weight
  },httpOptions);
 }

}