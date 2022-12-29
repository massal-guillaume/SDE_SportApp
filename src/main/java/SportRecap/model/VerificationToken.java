package SportRecap.model;

import SportRecap.security.JWTUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class VerificationToken {

    private int idUser;

    private String token;

    private Date expirationTime;


    public VerificationToken(User user) {
        super();
        this.token = UUID.randomUUID().toString();
        this.idUser = user.getId();
        this.expirationTime= expidate(JWTUtil.EXPIRATION_REGISTER_TOKEN);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime= expidate(JWTUtil.EXPIRATION_REGISTER_TOKEN);
    }

    public VerificationToken(int idUser,String token,Date expirationTime) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.idUser = idUser;
    }

    private Date expidate(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }




}
