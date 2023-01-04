package SportRecap.controller;

import SportRecap.model.*;
import SportRecap.service.AccountService;
import SportRecap.service.EmailSenderService;
import SportRecap.service.ExerciceService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;;
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


    private final AccountService accountService;
    private final ExerciceService exerciceService;

    private final JavaMailSender javaMailSender;
    private final EmailSenderService emailSenderService;

    @Autowired
    public SportController(AccountService accountService,ExerciceService exerciceService,EmailSenderService emailSenderService,JavaMailSender javaMailSender) throws SQLException, JSONException, IOException {
        this.accountService = accountService;
        this.exerciceService = exerciceService;
        this.emailSenderService = emailSenderService;
        this.javaMailSender = javaMailSender;
        //this.exerciceService.grabExercice();
    }

    private String getAppUrl(HttpServletRequest request){
        return "http://" +
                request.getServerName() +
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    @PostMapping("/resetpasswordRequest")
    public String resetPassword(@RequestParam("email") String email, HttpServletRequest request) throws SQLException {
        User user =  this.accountService.findUserbyEmail(email);
        if(user!=null){
            this.emailSenderService.sendMail(user,this.accountService.createPasswordToken(user).getToken(),getAppUrl(request),"requestresetpassword",this.javaMailSender);
        }
        return "An Email have been send on your mailbox to reset your password";
    }

    @PostMapping(path="/register")
    public String saveUser(@RequestBody UserModel usermodel, final HttpServletRequest request) throws Exception{
        User user = this.accountService.addNewUser(usermodel);
        if(user != null){
            this.emailSenderService.sendMail(user,this.accountService.createVerifToken(user).getToken(),this.getAppUrl(request),"registration",this.javaMailSender);
            return user.getEmail()+"\n"+user.getUsername();
            }else{
            return "Username or email already used";
            }
    }
    @GetMapping("/confirmation")
    public String verifyRegistration(@RequestParam("token") String token) throws SQLException {
        if(accountService.verifyVerificationToken(token)){
            return "Your account as been confirmed \n Go back on the Login Page off the application to start use the app !";
        }else return "An error as occured, your confrmation link is not valid anymore, go back in the login page and ask for a new Confirmation Link ";
    }

    @GetMapping("/reconfirmation")
    public String resendConfirmation(@RequestParam("email") String email, HttpServletRequest request) throws SQLException {
        User user = this.accountService.findUserbyEmail(email);
        if(user==null) return  "Impossible to find your email, are you register ?";
        if(!user.isAccountactivated()) {
            VerificationToken token = this.accountService.getVerifTokenfromUser(user.getId());
            if(token != null){
               this.accountService.deleteVerifToken(token);
            }
            this.emailSenderService.sendMail(user,this.accountService.createPasswordToken(user).getToken(),this.getAppUrl(request),"registration",this.javaMailSender);
        }else {
            return "Your account is already confirmed";
        }
        return "An email have been send to confirm your account";
    }


    @GetMapping("/resetpassword")
    public String SendNewPassword(@RequestParam("token") String token) throws SQLException {
        User user =  this.accountService.findUserbyPasswordToken(token);
        if(user!=null){
            String password = this.accountService.generateRandomPassword();
            this.accountService.changepassword(password,user);
            this.emailSenderService.sendMail(user,password,"","resetpassword",this.javaMailSender);
            this.accountService.deletePasswordToken(token);
        }
        return "An Email have been send on your mailbox with your new password";
    }


    @PostMapping("/newpassword")
    public String ChangePassword(@RequestBody PasswordModel password, HttpServletRequest request) throws SQLException {
        User user = this.accountService.usernamefromrequest(request);
        if(password.length() < 5){ return "Password too short, try again pleas !";}
        else {
            this.accountService.changepassword(password.getPassword(), user);
            return "Password changed !";
        }
    }

    @GetMapping(path="/liste_exercice")
    public Collection<Exercice> getListeExos() throws SQLException {
        return this.exerciceService.listExercice();
    }


    @GetMapping(path="/save_exo/{id}")
    public ResponseEntity<String> save_exo(@PathVariable int id, HttpServletRequest request) throws SQLException, JSONException, IOException {
        User user = this.accountService.usernamefromrequest(request);
        if(!this.exerciceService.saveExercice(user, id)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Exercise already in your list");
        }
        return null;
    }


    @GetMapping(path="/exercice")
    public Collection<Exercice> getUserExercice( HttpServletRequest request) throws SQLException {
        return this.exerciceService.getUserExercice(this.accountService.usernamefromrequest(request));
    }

    @PostMapping(path="/exercice/{exoId}/{weight}")
    public void addNewCharge(@PathVariable int exoId,@PathVariable int weight) throws SQLException {
        this.exerciceService.addNewCharge(exoId,weight);
    }

    @GetMapping(path="/category")
    public Collection<String> getCattegory() throws SQLException{
       return this.exerciceService.getCategory();
    }


}
