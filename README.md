# SDE_SportApp

SportRecap is an application that allows you to follow your evolution over time, in gym exercises.

## Endpoint : 

Base URL : http://localhost:8081

## REGISTER 
Post : /register

Body : 
{
        "email": "example@gmail.com",
        "username" : "example",
        "password" : "1234"
}

Response : 

{
    "email": "example@gmail.com",
    "username": "example"
}
## LOGIN 

Post : /login 
{
  "username" : "example, 
  "password" : "1234"

}

Response : 

{
    "access-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.MDgxL2xvZ2luIiwiZXhwIjoxNjczOTUyMDYwfQ.A52pATOGd-e9UGTWztlaatfvUP_0lNuzIVHia6ZhZQk"
}

## RECONFIRMATION

Post : /reconfirmation
{
  "email"= "example@gmail.com"
}

Response : 
{
  "email": "example@gmail.com",
   "username": "example"
}

## /resetpasswordRequest 

Get /resetpasswordRequest
{
    "email"= "example@gmail.com"
}

Response 

{
  "An Email have been send on your mailbox with your new password"
}

## ENDPOINT That need AUTH TOKEN : 

## NewPassword
POST /newpassword
{
  "password" = "newpassword"
}

## liste_exercice

GET /liste_exercice

Response : 
{
        "id": 1,
        "name": "2 Handed Kettlebell Swing",
        "categorie": "Abs",
        "currentWeight": 0,
        "muscle": "",
        "description": "<p>Two Handed Russian Style Kettlebell swing</p>",
        "history": null
 }

## Save EXO 

POST /save_exo
{
  "id"=3
}

## EXERCICE

GET /exercice

RESPONSE : 
[
{
        "id": 1,
        "name": "2 Handed Kettlebell Swing",
        "categorie": "Abs",
        "currentWeight": 0,
        "muscle": "",
        "description": "<p>Two Handed Russian Style Kettlebell swing</p>",
        "history": {}
    }
]

POST /exercice
{
  "id" = 3,
  "weight" = 4

}

## Category 

GET /category 

Response : 
{
  "category":
   [
    {
    "name"= "chest"
    },
    {
    "name"="legs"
    }
  
   ]

}










