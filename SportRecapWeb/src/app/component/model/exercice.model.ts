export class Exercice{

    id!:number;
    name:string="";
    categorie:string="";
    currentWeight:number=0;
    muscle:string="";
    description:string="";
    history = new Map<string,number>;

}