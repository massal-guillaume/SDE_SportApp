package SportRecap.model.mail;

import org.springframework.mail.javamail.JavaMailSender;

public interface Mail {

    void sendEmail(String email, String url,String token);

}
