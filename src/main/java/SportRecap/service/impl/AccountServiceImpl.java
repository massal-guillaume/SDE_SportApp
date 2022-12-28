package SportRecap.service.impl;

import SportRecap.DAO.AccountRepository;
import SportRecap.DAO.PasswordTokenRepository;
import SportRecap.DAO.VerifTokenRepository;
import SportRecap.model.*;
import SportRecap.security.JWTUtil;
import SportRecap.service.AccountService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private VerifTokenRepository verifTokenRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRepository accountRepository;

    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public AccountServiceImpl(VerifTokenRepository verifTokenRepository, PasswordEncoder passwordEncoder, AccountRepository accountRepository,PasswordTokenRepository passwordTokenRepository) {
        this.accountRepository = accountRepository;
        this.verifTokenRepository = verifTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordTokenRepository= passwordTokenRepository;
    }


    @Override
    public User addNewUser(UserModel userModel) throws SQLException {
        User user = new User();
        if (findUserbyEmail(userModel.getEmail()) == null) {
            if (findUserbyUsername(userModel.getUsername()) == null) {
                try {
                    user.setUsername(userModel.getUsername());
                    user.setEmail(userModel.getEmail());
                    String password = userModel.getPassword();
                    user.setPassword(passwordEncoder.encode(password));
                    User userSaved = this.accountRepository.save(user);
                    return userSaved;
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return null;
    }

    @Override
    public User findUserbyUsername(String username) throws SQLException {
        return this.accountRepository.findByUsername(username);
    }

    @Override
    public User findUserbyEmail(String email) throws SQLException {
        return this.accountRepository.findByEmail(email);
    }

    @Override
    public List<User> listUsers() throws SQLException {
        return accountRepository.findAll();
    }

    @Override
    public VerificationToken createVerifToken(User user) throws SQLException {
        VerificationToken veriftoken = new VerificationToken(user);
        this.verifTokenRepository.save(veriftoken);
        return veriftoken;
    }

    @Override
    public Boolean verifyVerificationToken(String token) throws SQLException {
        VerificationToken verificationToken = verifTokenRepository.findByToken(token);
        if(verificationToken == null) return false;
        User user = finUserbyId(verificationToken.getIdUser());
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime()-cal.getTime().getTime() <=0){
            this.verifTokenRepository.delete(verificationToken);
            return false;
        }
        if(user.isAccountactivated()==true) return false;
        user.setAccountactivated(true);
        accountRepository.update(user);
        this.verifTokenRepository.delete(verificationToken);
        return true;
    }

    private User finUserbyId(long idUser) throws SQLException {
        return this.accountRepository.findById(idUser);

    }
    @Override
    public VerificationToken getVerifTokenfromUser(long id) throws SQLException {
        VerificationToken token = this.verifTokenRepository.getVerifTokenById(id);
        if(token == null) return null;
        else return token;
    }

    public void deleteVerifToken(VerificationToken verificationToken) throws SQLException {
        this.verifTokenRepository.delete(verificationToken);

    }

    public void changepassword(String password,User user) throws SQLException{
        this.accountRepository.changepassword(passwordEncoder.encode(password),user);
    }

    public String generateRandomPassword(){
        String password = UUID.randomUUID().toString();
        return password;
    }

    @Override
    public PasswordToken createPasswordToken(User user) throws SQLException {
        PasswordToken passwordToken= new PasswordToken((int) user.getId());
        this.passwordTokenRepository.save(passwordToken);
        return passwordToken;
    }

    @Override
    public User findUserbyPasswordToken(String token) throws SQLException {
       int id =  this.passwordTokenRepository.findUserByToken(token).getIdUser();
       User user = this.accountRepository.findById(id);
       return user;
    }

    @Override
    public void deletePasswordToken(String token) throws SQLException {
        this.passwordTokenRepository.deletePasswordToken(token);
    }

    @Override
    public User usernamefromrequest(HttpServletRequest request) throws SQLException {
        try {
            String authtoken = request.getHeader(JWTUtil.AUTH_HEADER);
            String token = authtoken.substring(JWTUtil.PREFIX.length());
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            String username = decodedJWT.getSubject();
            return this.accountRepository.findByUsername(username);
        } catch (Exception e) {
            throw e;
        }
    }

/*


    @Override
    public void deleteToken(VerificationToken token) {
        this.verifTokenRepository.delete(token);
    }

    @Override
    public String createPasswordToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordToken passwordToken = new PasswordToken(token,user);
        this.passwordTokenRepository.save(passwordToken);
        return token;
    }

    @Override
    public boolean veryPasswordToken(String token, PasswordModel passwordModel) {
        PasswordToken passwordToken = this.passwordTokenRepository.findByToken(token);
        if(passwordToken == null) return false;

        User user = passwordToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(passwordToken.getExpirationTime().getTime()-cal.getTime().getTime() <=0){
            this.passwordTokenRepository.delete(passwordToken);
            return false;
        }
        user.setPassword(passwordEncoder.encode(passwordModel.getPassword()));
        userRepository.save(user);
        return true;
    }
*/
}