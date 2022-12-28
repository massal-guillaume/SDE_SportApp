package SportRecap.service;

import SportRecap.model.*;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface AccountService {


    User addNewUser(UserModel userModel) throws SQLException;

    User findUserbyUsername(String username) throws SQLException;

    User findUserbyEmail(String email) throws SQLException;

    List<User> listUsers() throws SQLException;

    VerificationToken createVerifToken(User user) throws SQLException;

    Boolean verifyVerificationToken(String token) throws SQLException;

    VerificationToken getVerifTokenfromUser(long id) throws SQLException;

    void deleteVerifToken(VerificationToken token) throws SQLException;

    public void changepassword(String password,User user) throws SQLException;
    public String generateRandomPassword();

    PasswordToken createPasswordToken(User user) throws SQLException;

    User findUserbyPasswordToken(String token) throws SQLException;

    void deletePasswordToken(String token) throws SQLException;


    User usernamefromrequest(HttpServletRequest request) throws SQLException;

/*
    Boolean verifyVerificationToken(String token);


    String createPasswordToken(User user);

    void sendResetPasswordMail(User user, String token,String url);

    boolean veryPasswordTolen(String token, String password);

*/
}
