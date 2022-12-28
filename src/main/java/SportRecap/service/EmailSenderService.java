package SportRecap.service;

import SportRecap.model.User;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailSenderService {

    public void sendMail(User user, String token, String url, String mailtype, JavaMailSender javaMailSender);

}
