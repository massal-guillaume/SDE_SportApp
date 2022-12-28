package SportRecap.model.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailRegistration implements Mail {

        private JavaMailSender mailSender;
        @Autowired
        public  MailRegistration(JavaMailSender mailSender) {
            this.mailSender = mailSender;
        }

        @Override
        public void sendEmail(String toEmail,String url,String token){

            String body = "Bonjour,\n" +
                    "\n" +
                    "Vous venez de vous inscrire sur l'application Sport Recap.\n" +
                    "\n" +
                    "Nous vous souhaitons la bienvenue ! Veuillez cliquer sur le lien ci-dessous pour valider votre compte."+"\n"+"\n"+url+ "/confirmation?token="+token;



            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sport.recap.12@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject("Confirmation de votre compte Sport Recap");

            mailSender.send(message);

        }
}
