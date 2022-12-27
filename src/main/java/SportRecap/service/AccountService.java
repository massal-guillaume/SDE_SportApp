package SportRecap.service;

import SportRecap.model.Exercice;
import SportRecap.model.User;
import SportRecap.model.UserModel;
import SportRecap.model.VerificationToken;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface AccountService {


    User addNewUser(UserModel userModel) throws SQLException;

    User findUserbyUsername(String username) throws SQLException;

    User findUserbyEmail(String email) throws SQLException;

    List<User> listUsers() throws SQLException;
/*
    String usernamefromrequest(HttpServletRequest request);

    void saveVerifToken(String token, User user);

    Boolean verifyVerificationToken(String token);

    VerificationToken getTokenfromUser(User user);

    void deleteToken(VerificationToken token);

    String createPasswordToken(User user);

    void sendResetPasswordMail(User user, String token,String url);

    boolean veryPasswordTolen(String token, String password);

*/
}
