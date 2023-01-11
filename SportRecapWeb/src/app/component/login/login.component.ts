import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/_services/auth.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  reconfirm:boolean=false;
  reconfirmEmail= "";
  reconfirmfail:boolean=false;


  constructor(private authService: AuthService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }

  onSubmit(): void {
    const { username, password } = this.form;

    this.authService.login(username, password).subscribe(
      data => {
        this.tokenStorage.saveToken(data['access-token']);
        this.tokenStorage.saveRefreshToken(data['refresh-token']);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error;   
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }

  reconfirmation(email : string): void {
    this.authService.reconfirm(email).subscribe(
    data => {
      this.reconfirm=true;
      this.reconfirmfail = false;
    },
    err => {
      this.errorMessage = err.error;
      this.reconfirmfail = true;
      this.reconfirm=false;
    }
  );
    
  }
}