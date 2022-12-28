package SportRecap.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordModel {
    private String password;

    public int length() {
        return password.length();
    }
}
