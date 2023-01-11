import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenStorageService } from './token-storage.service';

const AUTH_API = environment.host;

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient,private tokenStorage: TokenStorageService) { }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + "/register", {
      email,
      username,
      password
    });
  }

  reconfirm(email: string): Observable<any>{
    return this.http.get(AUTH_API +"/reconfirmation?email="+email);
  }

  //Recupere une nouveau Acess Token grace au refresh Token
  refreshAccesToken(refreshtoken:string):Observable<any>{
    var httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json', Authorization: 'Bearer '+this.tokenStorage.getRefreshToken()! })}
    return this.http.get(AUTH_API + "/refreshtoken",httpOptions);
  }
  
  //Verifie si le Access Token est valide
  tokenExpired(token: string) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return (Math.floor((new Date).getTime() / 1000)) >= expiry;
  }


  checktokenExpired(){
      if(this.tokenExpired(this.tokenStorage.getToken()!) ){ 
        this.refreshAccesToken(this.tokenStorage.getRefreshToken()!).subscribe(
          data => {
            this.tokenStorage.saveToken(data['access-token']);
          },
        );  
       }     
  }
  


  login(Username:string,Password:string):Observable<any>{
         
    let body = new FormData();
    body.append('username', Username);
    body.append('password', Password);
    return this.http.post(AUTH_API+"/login", body).pipe(catchError((this.handleError)));    
    
}

  handleError(error: HttpErrorResponse) {
      return throwError(error.message);
    }


}