import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from  '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { MesExercicesComponent } from './component/mes-exercices/mes-exercices.component';
import { AjoutComponent } from './component/ajout/ajout.component';
import { NgApexchartsModule } from 'ng-apexcharts';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MesExercicesComponent,
    AjoutComponent
  ],
  imports: [
    NgApexchartsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'exercices', component:MesExercicesComponent},
    {path: 'ajout', component:AjoutComponent},
    { path: '', redirectTo: 'exercices', pathMatch: 'full' }
      

    ]),
    
  

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
