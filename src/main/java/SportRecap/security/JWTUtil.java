package SportRecap.security;

import org.springframework.core.env.Environment;

public class JWTUtil {

    //Security
    public static final String SECRET = "codesecret1245";
    public static final String AUTH_HEADER="Authorization";
    public static final long EXPIRE_ACESS_TOKEN=10*60*10000;
    public static final long EXPIRE_REFRESH_TOKEN=10000*60*1000;
    public static final String PREFIX = "Bearer ";
    public static final int EXPIRATION_REGISTER_TOKEN = 10;
    public static final int EXPIRATION_PASSWORD_TOKEN=5;

    //MYSQL
    public static final String JDBC_URL ="jdbc:mysql://localhost:3306/sde?autoReconnect=true&useSSL=false";
    public static final String JDBC_USERNAME="root";
    public static final String JDBC_PASSWORD="A2000091223";

    //API
    public static final String API_USERNAME = "sportrecap";
    public static final String API_PASSWORD =  "spr12345";


}
