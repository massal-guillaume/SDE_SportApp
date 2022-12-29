package SportRecap.controller;

import SportRecap.model.*;
import SportRecap.service.AccountService;
import SportRecap.service.EmailSenderService;
import SportRecap.service.ExerciceService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@RestController
public class SportController {


    private AccountService accountService;
    private ExerciceService exerciceService;
    private ApplicationEventPublisher publisher;

    private JavaMailSender javaMailSender;
    private EmailSenderService emailSenderService;

    @Autowired
    public SportController(AccountService accountService,ExerciceService exerciceService,ApplicationEventPublisher publisher,EmailSenderService emailSenderService,JavaMailSender javaMailSender) {
        this.accountService = accountService;
        this.exerciceService = exerciceService;
        this.emailSenderService = emailSenderService;
        this.javaMailSender = javaMailSender;
        this.publisher=publisher;
    }

    private String getAppUrl(HttpServletRequest request){
        return "http://" +
                request.getServerName() +
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    @PostMapping("/resetpasswordRequest")
    public ResponseEntity resetPassword(@RequestParam("email") String email, HttpServletRequest request) throws SQLException {
        User user =  this.accountService.findUserbyEmail(email);
        if(user!=null){
            this.emailSenderService.sendMail(user,this.accountService.createPasswordToken(user).getToken(),getAppUrl(request),"requestresetpassword",this.javaMailSender);
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("An Email have been send on your mailbox to reset your password");
    }

    @PostMapping(path="/register")
    public ResponseEntity saveUser(@RequestBody UserModel usermodel, final HttpServletRequest request) throws Exception{
        User user = this.accountService.addNewUser(usermodel);
        if(user != null){
            this.emailSenderService.sendMail(user,this.accountService.createVerifToken(user).getToken(),this.getAppUrl(request),"registration",this.javaMailSender);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(user.getEmail()+"\n"+user.getUsername());
            }else{
            return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Username or email already used");
            }
    }
    @GetMapping("/confirmation")
    public String verifyRegistration(@RequestParam("token") String token) throws SQLException {
        if(accountService.verifyVerificationToken(token)){
            return "Your account as been confirmed \n Go back on the Login Page off the application to start use the app !";
        }else return "An error as occured, your confrmation link is not valid anymore, go back in the login page and ask for a new Confirmation Link ";
    }

    @GetMapping("/reconfirmation")
    public ResponseEntity resendConfirmation(@RequestParam("email") String email,final HttpServletRequest request) throws SQLException {
        User user = this.accountService.findUserbyEmail(email);
        if(user==null) return  ResponseEntity.status(HttpStatus.ACCEPTED).body("Impossible to find your email, are you register ?");
        if(user.isAccountactivated()==false) {
            VerificationToken token = this.accountService.getVerifTokenfromUser(user.getId());
            if(token != null){
               this.accountService.deleteVerifToken(token);
            }
            this.emailSenderService.sendMail(user,this.accountService.createPasswordToken(user).getToken(),this.getAppUrl(request),"registration",this.javaMailSender);
        }else {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Your account is already confirmed");
        }
        return  ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("An email have been send to confirm your account");
    }


    @GetMapping("/resetpassword")
    public ResponseEntity SendNewPassword(@RequestParam("token") String token) throws SQLException {
        User user =  this.accountService.findUserbyPasswordToken(token);
        if(user!=null){
            String password = this.accountService.generateRandomPassword();
            this.accountService.changepassword(password,user);
            this.emailSenderService.sendMail(user,password,"","resetpassword",this.javaMailSender);
            this.accountService.deletePasswordToken(token);
        }
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("An Email have been send on your mailbox with your new password");
    }


    @PostMapping("/newpassword")
    public ResponseEntity ChangePassword(@RequestBody PasswordModel password, final HttpServletRequest request) throws SQLException {
        User user = this.accountService.usernamefromrequest(request);
        if(password.length() < 5){ return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Password too short, try again pleas !");}
        else {
            this.accountService.changepassword(password.getPassword(), user);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Password changed !");
        }
    }

    @GetMapping(path="/exercice")
    public Collection<Exercice> getexos(HttpServletRequest request) throws SQLException {
        return this.exerciceService.listExercice(this.accountService.usernamefromrequest(request).getId());
    }

    @GetMapping(path="/newexo")
    public void newexos () throws JSONException, IOException {
       this.exerciceService.getToken();
    }


/*

    @GetMapping(path="/user")
    public User getUser(HttpServletRequest request) {
        return this.userService.findUserbyUsername(this.userService.usernamefromrequest(request));
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
