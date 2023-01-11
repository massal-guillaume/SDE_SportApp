import { HttpClient, HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import  {createPlatform, EnvironmentInjector, Injectable} from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenParam } from '../component/model/tokenparam.model';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';

@Injectable({providedIn:"root"})
export class TokenAccessManager{


   

    constructor(private http:HttpClient){

    }

    login(Username:string,Password:string):Observable<any>{
        
    
        let body = new FormData();
        body.append('username', Username);
        body.append('password', Password);
        return this.http.post(environment.host+"/login", body).pipe(catchError((this.handleError)));
        
        
            
        
    }

    handleError(error: HttpErrorResponse) {
        return throwError(error.message);
      }

    //Methode Post refreshToken

    

}