package SportRecap.DAO;

import SportRecap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {


    private Statement connection;

    @Autowired
    AccountRepository(MysqlConnection mysqlConnection){
        this.connection = mysqlConnection.connection();
    }

    public User findByUsername(String username) throws SQLException {

        Long id = null;
        String email = null;
        Boolean accountactivated = null;
        String name = null;
        String password = null;

        ResultSet res = this.connection.executeQuery("SELECT * FROM user WHERE username=" + username);

        while (res.next()) {
            id = res.getLong(1);
            email = res.getString(2);
            accountactivated = res.getBoolean(4);
            name = res.getString(5);
            password = res.getString(6);
        }
        if(id.equals(null)){
            throw new RuntimeException();
        }else {
            User user = new User(id, email, accountactivated, name, password);
            return user;
        }
    }

    public User findByEmail(String email) throws SQLException {
        Long id = null;
        String mail = null;
        Boolean accountactivated = null;
        String username = null;
        String password = null;

        ResultSet res = this.connection.executeQuery("SELECT * FROM user WHERE email=" + email);

        while (res.next()) {
            id = res.getLong(1);
            email = res.getString(2);
            accountactivated = res.getBoolean(4);
            username = res.getString(5);
            password = res.getString(6);
        }
        if(id.equals(null)){
            throw new RuntimeException();
        }else {
            User user = new User(id, email, accountactivated, username, password);
            return user;
        }
    }

    public void save(User user) throws SQLException {
        ResultSet res = this.connection.executeQuery("INSERT INTO user VALUES ("+user.getEmail()+","+user.isAccountactivated()+","+user.getUsername()+","+user.getPassword()+")");
    }

    public List<User> findAll() throws SQLException {
        Long id = null;
        String email = null;
        Boolean accountactivated = null;
        String username = null;
        String password = null;
        List<User> listeUser = new ArrayList<>();

        ResultSet res = this.connection.executeQuery("SELECT * FROM user");

        while (res.next()) {
            id = res.getLong(1);
            email = res.getString(2);
            accountactivated = res.getBoolean(4);
            username = res.getString(5);
            password = res.getString(6);
            User user = new User(id, email, accountactivated, username, password);
            listeUser.add(user);
        }
        return listeUser;
    }
}
