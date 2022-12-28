package SportRecap.DAO;

import SportRecap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository {


    private final ConnectionPoolManager pool;

    @Autowired
    public AccountRepository(ConnectionPoolManager connectionPoolManager){

        this.pool = connectionPoolManager;
    }

    public User findByUsername(String username) throws SQLException {
        Long id = null;
        String email = null;
        Boolean accountactivated = null;
        String name = null;
        String password = null;

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        stat.setString(1, username);
        ResultSet res = stat.executeQuery();

        while (res.next()) {
            id = res.getLong(1);
            email = res.getString(2);
            accountactivated = res.getBoolean(3);
            name = res.getString(4);
            password = res.getString(5);
        }
        connection.close();
        if(id == null){
                return null;
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

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM user WHERE email= ?");
        stat.setString(1, email);
        ResultSet res = stat.executeQuery();

        while (res.next()) {
            id = res.getLong(1);
            email = res.getString(2);
            accountactivated = res.getBoolean(3);
            username = res.getString(4);
            password = res.getString(5);
        }
        connection.close();
        if(id == null){
                return null;
        }else {
            User user = new User(id, email, accountactivated, username, password);
            return user;
        }
    }

    public User save(User user) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  user (`email`, `isaccountactivated`, `username`, `password`) VALUES (?,?,?,?)");
        stat.setString(1, user.getEmail());
        stat.setBoolean(2,false);
        stat.setString(3, user.getUsername());
        stat.setString(4, user.getPassword());
        stat.executeUpdate();
        connection.close();
        User userSaved = findByUsername(user.getUsername());
        return userSaved;
    }


    public List<User> findAll() throws SQLException {
        Long id = null;
        String email = null;
        Boolean accountactivated = null;
        String username = null;
        String password = null;
        List<User> listeUser = new ArrayList<>();


        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM user");
        ResultSet res = stat.executeQuery();

        while (res.next()) {
            id = res.getLong(1);
            email = res.getString(2);
            accountactivated = res.getBoolean(4);
            username = res.getString(5);
            password = res.getString(6);
            User user = new User(id, email, accountactivated, username, password);
            listeUser.add(user);
        }
        connection.close();
        return listeUser;
    }

    public User findById(long idUser) throws SQLException {
        Long id = null;
        String email = null;
        Boolean accountactivated = null;
        String username = null;
        String password = null;

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM user WHERE id= ?");
        stat.setLong(1, idUser);
        ResultSet res = stat.executeQuery();

        while (res.next()) {
            id = idUser;
            email = res.getString(2);
            accountactivated = res.getBoolean(3);
            username = res.getString(4);
            password = res.getString(5);
        }
        connection.close();
        if(id == null){
            return null;
        }else {
            User user = new User(id, email, accountactivated, username, password);
            return user;
        }
    }

    public void update(User user) throws SQLException {
        if(user!=null){
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("UPDATE user SET  email=?,isaccountactivated=?,username =? WHERE id=?");
        stat.setString(1, user.getEmail());
        stat.setBoolean(2, user.isAccountactivated());
        stat.setString(3, user.getUsername());
        stat.setLong(4, user.getId());
        stat.executeUpdate();
        connection.close();
        }
    }

    public void changepassword(String password,User user) throws SQLException {
        if(user!=null){
            Connection connection = this.pool.getConnection();
            PreparedStatement stat = connection.prepareStatement("UPDATE user SET  password =? WHERE id=?");
            stat.setString(1, password);;
            stat.setLong(2, user.getId());
            stat.executeUpdate();
            connection.close();
        }
    }
}
