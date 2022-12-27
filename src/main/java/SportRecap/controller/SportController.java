package SportRecap.controller;

import SportRecap.model.Exercice;
import SportRecap.model.User;
import SportRecap.model.UserModel;
import SportRecap.model.VerificationToken;
import SportRecap.security.JWTUtil;
import SportRecap.service.AccountService;
import SportRecap.service.ExerciceService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SportController {


    private AccountService userService;
    private ExerciceService exerciceService;
    private ApplicationEventPublisher publisher;

    public SportController(AccountService accountService,ExerciceService exerciceService,ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.exerciceService = exerciceService;
        this.publisher=publisher;
    }
/*
    private String getAppUrl(HttpServletRequest request){
        return "http://" +
                request.getServerName() +
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@RequestParam("email") String email, HttpServletRequest request){
        User user =  this.userService.findUserbyEmail(email);
        if(user!=null){
            String token = this.userService.createPasswordToken(user);
            this.userService.sendResetPasswordMail(user,token,getAppUrl(request));

        }
        return "Un email à été envoyer à l'adresse email pour réinitialiser votre mot de passe";
    }

*/
    @PostMapping(path="/register")
    public ResponseEntity saveUser(@RequestBody UserModel usermodel, final HttpServletRequest request) throws Exception{
        System.out.println("REGISTER");
        if(this.userService.addNewUser(usermodel)!=null){
            //send MAIL
            }else{
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Username or email already used");
            }
        return null;
    }
/*
    @GetMapping("/newpassword")
    public String newPassword(@RequestParam("token") String token,@RequestBody String passwordModel){
        if(userService.veryPasswordTolen(token,passwordModel)){
            return "Nouveau mot de passe bien modifier";
        }return "Un probleme est survenue, veuillez reesayer";

    }

    @GetMapping("/confirmation")
    public String verifyRegistration(@RequestParam("token") String token){
        if(userService.verifyVerificationToken(token)){
            return "Confirmation bien effectue";
        }else return "Erreur, veuillez re demandez une email de confirmation si vous n'etes pas inscrit";
    }

    @GetMapping("/reconfirmation")
    public String resendConfirmation(@RequestParam("email") String email,final HttpServletRequest request){
        User user = this.userService.findUserbyEmail(email);
        if(this.userService.findUserbyEmail(email)==null) return "Adresse Mail introuvable, veuillez vous inscrire";
        if(user.isAccountactivated()==false) {

            if(this.userService.getTokenfromUser(user) != null){
                VerificationToken token = this.userService.getTokenfromUser(user);
                this.userService.deleteToken(token);
            }
            publisher.publishEvent(new SendConfirmationEvent(user,getAppUrl(request)));
        }else return "Votre compte a deja été confirmer";
        return "Nouveau Lien de Confirmation Envoyer !";
    }

    @GetMapping(path="/user")
    public User getUser(HttpServletRequest request) {
        return this.userService.findUserbyUsername(this.userService.usernamefromrequest(request));
    }

    @GetMapping(path="/refreshtoken")
    public void rereshtoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authtoken = request.getHeader(JWTUtil.AUTH_HEADER);
        if(authtoken!= null && authtoken.startsWith(JWTUtil.PREFIX)){
            try {
                String token = authtoken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                User user = this.userService.findUserbyUsername(username);
                String jwttokenaccess = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);
                Map<String,String> idtoken = new HashMap<>();
                idtoken.put("access-token",jwttokenaccess);
                idtoken.put("refresh-token",token);
                response.setContentType("aplication/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idtoken);
            }catch (Exception e){
                throw e;
            }
        }else{
            throw new RuntimeException("Refresh Token required");
        }
    }

    @PostMapping(path ="/exercice")
    public ResponseEntity saveExercice(@RequestBody Exercice exo, HttpServletRequest request){
        try {
            this.userService.addNewExercice(this.userService.usernamefromrequest(request),exo);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Il y a déjà un exercice du meme nom");
        }
        return null;
    }

    @GetMapping(path="/exercice")
    public Collection<Exercice> getexos(HttpServletRequest request){
        return this.userService.listExercice(this.userService.usernamefromrequest(request));
    }

    @GetMapping(path="/exercice/{categorie}")
    public Collection<Exercice> getexocategorie(@PathVariable String categorie, HttpServletRequest request){
        return this.userService.categorieExo(this.userService.usernamefromrequest(request),categorie);
    }

    @PostMapping(path="/exercice/{charge}")
    public void addNewCharge(@PathVariable String categorie){

    }
*/
}
