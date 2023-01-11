package SportRecap.model.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailRegistration implements Mail {

        private final JavaMailSender mailSender;
        @Autowired
        public  MailRegistration(JavaMailSender mailSender) {
            this.mailSender = mailSender;
        }

        @Override
        public void sendEmail(String toEmail,String url,String token){

            String body = "Hello,\n" +
                    "\n" +
                    "You have just registered on the Sport Recap application.\n" +
                    "\n" +
                    "Please click on the link below to validate your account."+"\n"+"\n"+url+ "/confirmation?token="+token;



            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sport.recap.12@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject("Confirmation de votre compte Sport Recap");

            mailSender.send(message);

        }
}
