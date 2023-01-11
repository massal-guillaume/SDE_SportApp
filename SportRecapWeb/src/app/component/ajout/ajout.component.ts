import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { FormBuilder } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Exercice } from '../model/exercice.model';
import { AuthService } from 'src/app/_services/auth.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-ajout',
  templateUrl: './ajout.component.html',
  styleUrls: ['./ajout.component.css']
})
export class AjoutComponent implements OnInit {
  
  exerciceListed !: Array<Exercice>;
  categorie !: Array<string>
  default = 'ALL'
  selectedCategorie:string;
  detailexo !: string;

  errorMessage : string = "";


 constructor( private tokenStorage: TokenStorageService, private auth: AuthService,private userService: UserService) { 
   this.selectedCategorie=this.default;
 }

 ngOnInit(): void { 
   this.auth.checktokenExpired();
   this.userService.getCategory().subscribe(
     data => {
      if(data !=null){
       this.categorie = data;
       console.log(data);
      }
     },
   );
   this.userService.getExercicesListe().subscribe(
    data => {
      if(data !=null){
       this.exerciceListed = data;
       console.log(data);
      }
     },
   );
   }

   detail(exercice:Exercice): void{
     this.detailexo=exercice.description;
     Swal.fire(this.detailexo);
   }

    add(exercice:Exercice): void{
    this.userService.addExerciceInUserListe(exercice.id).subscribe(
      data => {
        Swal.fire("The exercise have been saved in your list !")
      },
      err => {
        this.errorMessage = err.error;
        Swal.fire(this.errorMessage);
      }
    );

    }



}
