package SportRecap.service.impl;

import SportRecap.model.User;
import SportRecap.model.mail.Mail;
import SportRecap.model.mail.MailRequestResetPassword;
import SportRecap.model.mail.MailResetPassword;
import SportRecap.model.mail.MailRegistration;
import SportRecap.service.EmailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {


    private Mail mail;
    @Override
    public void sendMail(User user, String token, String url, String mailtype, JavaMailSender javaMailSender) {
        switch (mailtype){
            case "registration" :
                this.mail = new MailRegistration(javaMailSender);
                 break;
            case "resetpassword" :
                this.mail = new MailResetPassword(javaMailSender);
                break;
            case "requestresetpassword" :
                this.mail = new MailRequestResetPassword(javaMailSender);
        }
        this.mail.sendEmail(user.getEmail(), url, token);
    }


}
