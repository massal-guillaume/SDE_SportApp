package SportRecap.model;

import SportRecap.security.JWTUtil;

import java.util.Calendar;
import java.util.Date;

public class VerificationToken {


    private long id;

    private String token;

    private Date expirationTime;

    private User user;

    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime= expidate(JWTUtil.EXPIRATION_REGISTER_TOKEN);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime= expidate(JWTUtil.EXPIRATION_REGISTER_TOKEN);
    }

    private Date expidate(int expirationTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }




}
