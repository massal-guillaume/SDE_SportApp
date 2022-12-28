package SportRecap.model;

import SportRecap.security.JWTUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class PasswordToken {


    private int idUser;

    private String token;

    private Date expirationTime;


    public PasswordToken(int idUser) {
        super();
        this.token =  UUID.randomUUID().toString();
        this.idUser = idUser;
        this.expirationTime= expidate(JWTUtil.EXPIRATION_PASSWORD_TOKEN);
    }

    public PasswordToken(String token) {
        super();
        this.token = token;
        this.expirationTime= expidate(JWTUtil.EXPIRATION_PASSWORD_TOKEN);
    }

    public PasswordToken(int idUser, String token, Date date) {
        this.token =  token;
        this.idUser = idUser;
        this.expirationTime= date;
    }

    private Date expidate(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }



}
