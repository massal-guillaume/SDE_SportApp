package SportRecap.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    private String email;
    private String username;
    private String password;

    public UserModel(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
