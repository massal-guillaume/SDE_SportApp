package SportRecap.model.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailRequestResetPassword implements  Mail{

    private JavaMailSender mailSender;


    public MailRequestResetPassword(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String toEmail,String url,String token) {

        String body = "Hello,\n" +
                "\n" +
                "After clicking on this link, your password will be reset .\n" +
                "\n" + "Do not click if you are not the one that send us a reset request"+
                "\n"+
                "Click ->" + "   "+url+"/resetpassword?token="+token;


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sport.recap.12@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject("Sport Recap App Password Reset");

        mailSender.send(message);
    }
}
