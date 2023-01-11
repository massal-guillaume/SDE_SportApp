package SportRecap.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseModel {

        private String email;
        private String username;

        public UserResponseModel(String email, String username) {
            this.email = email;
            this.username = username;

        }

}
