import { Component, createPlatform, OnInit } from '@angular/core';
import { Exercice } from '../model/exercice.model';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { AuthService } from 'src/app/_services/auth.service';
import { UserService } from 'src/app/_services/user.service';
import Swal from 'sweetalert2';
import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexTitleSubtitle,
  ApexStroke,
  ApexGrid
} from "ng-apexcharts";
import { DeclarationListEmitMode } from '@angular/compiler';


@Component({
  selector: 'app-mes-exercices',
  templateUrl: './mes-exercices.component.html',
  styleUrls: ['./mes-exercices.component.css']
})
export class MesExercicesComponent implements OnInit {

   exercice !:Exercice[];
   categorie !: Array<string>
   default = 'ALL';
   selectedCategorie:string;
   detailexo = new Exercice;
   errorMessage:string = "";
   weight!:number;
   showDetail:boolean=false;
   displayTable:boolean=false;
   DontdisplayTable:boolean=true;
   series !:ApexAxisChartSeries;
   chart !:ApexChart;
   title!:ApexTitleSubtitle;
   xaxis!:ApexXAxis;

  constructor( private tokenStorage: TokenStorageService, private auth: AuthService,private userService: UserService) { 
    this.selectedCategorie=this.default;
  }

  ngOnInit(): void { 
    this.auth.checktokenExpired();
    this.userService.getCategory().subscribe(
      data => {
       if(data !=null){
        this.categorie = data;
       }
      },
    );
    this.userService.getExercices().subscribe(
      data => {
       if(data !=null){
        this.exercice = data;
        this.displayTable = true;
        this.DontdisplayTable=false;
       }
      },
    );
    }

    detail(exercice:Exercice):void{
      Swal.fire(exercice.description);
    }


    graph(exercice:Exercice): void{
      this.showDetail=true;
      console.log(exercice.history);
      let arr1: number[] = [];
      let arr2: string[] = [];
      Object.values(exercice.history).forEach(value => {
        arr1.push(value);
      });
      Object.keys(exercice.history).forEach(value => {
        arr2.push(value.substring(0,10));
      });
      
     
      this.title={
        text:"Evolution of your performance"
      };

      
      this.series = [{
        name: 'Java',
        data:  arr1
      }];

      this.xaxis =  {
        type: "datetime",
        categories: arr2
      }

      this.chart ={
        type: "area",
        width: "100%"
      };

    }

    save(exercice:Exercice,weight:number): void{
      if(weight<0){
        weight=0;
      }
      this.userService.saveWeight(exercice.id,weight).subscribe(
        data => {
          Swal.fire("The new Weight have been saved !");
        }
      );
    }

 

}
