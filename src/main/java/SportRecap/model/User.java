package SportRecap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private int id;
    private String email;
    private boolean accountactivated = false;
    private String username;
    private String password;

    public User(int id, String email, Boolean accountactivated, String username, String password) {
        this.id = id;
        this.email = email;
        this.accountactivated = accountactivated;
        this.username = username;
        this.password = password;
    }

    public boolean isAccountactivated() {
        return accountactivated;
    }


}
