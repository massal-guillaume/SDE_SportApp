package SportRecap.security;


import SportRecap.model.User;
import SportRecap.service.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountService accountService;

    public UserDetailsServiceImpl(AccountService AccountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = null;
        try {
            user = accountService.findUserbyUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user == null) {
            UsernameNotFoundException UsernameNotFoundException = new UsernameNotFoundException("Utilisateur ou mot de passe erroné");
            throw UsernameNotFoundException;
        }
        if(user.isAccountactivated() == false){
            UsernameNotFoundException UsernameNotFoundException = new UsernameNotFoundException("Veuillez confirmer votre compte avant de vous connecter");
            throw UsernameNotFoundException;
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<GrantedAuthority>());
    }

}