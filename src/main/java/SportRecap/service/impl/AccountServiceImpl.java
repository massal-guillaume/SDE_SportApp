package SportRecap.service.impl;

import SportRecap.DAO.AccountRepository;
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

    @Autowired
    public AccountServiceImpl(VerifTokenRepository verifTokenRepository, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.verifTokenRepository = verifTokenRepository;
        this.passwordEncoder = passwordEncoder;
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
                    this.accountRepository.save(user);
                    return user;
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return user;
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

    /*
    @Override
    public String usernamefromrequest(HttpServletRequest request) {
        try {

            String authtoken = request.getHeader(JWTUtil.AUTH_HEADER);
            String token = authtoken.substring(JWTUtil.PREFIX.length());
            Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            String username = decodedJWT.getSubject();
            return username;
        } catch (Exception e) {
            throw e;
        }
    }

     */
/*
    @Override
    public void saveVerifToken(String token, User user) {
        VerificationToken veriftoken = new VerificationToken(token,user);
        this.verifTokenRepository.save(veriftoken);

    }

    @Override
    public Boolean verifyVerificationToken(String token) {
        VerificationToken verificationToken = verifTokenRepository.findByToken(token);
        if(verificationToken == null) return false;

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime()-cal.getTime().getTime() <=0){
            this.verifTokenRepository.delete(verificationToken);
            return false;
        }
        if(user.isAccountactivated()==true) return false;
        user.setAccountactivated(true);
        userRepository.save(user);
        return true;

    }

    @Override
    public VerificationToken getTokenfromUser(User user) {

        VerificationToken token = this.verifTokenRepository.findByUser(user);
        if(token == null) return null;
        else return token;
    }

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