package SportRecap.model.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailResetPassword implements Mail {


    private JavaMailSender mailSender;


    public MailResetPassword(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendEmail(String toEmail,String url,String token) {

        String body = "This is your new password :\n" +token+
                "\n" +
                "Keep it carefully and do not share it with anyone\n" +
                "\n" +
                "U can now connect to your account and change this password" + "\n";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sport.recap.12@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject("New Password Sport Recap");

        mailSender.send(message);
    }

}
