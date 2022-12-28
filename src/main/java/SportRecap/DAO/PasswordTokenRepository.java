package SportRecap.DAO;


import SportRecap.model.PasswordToken;
import SportRecap.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PasswordTokenRepository {

    private final ConnectionPoolManager pool;

    @Autowired
    public PasswordTokenRepository(ConnectionPoolManager connectionPoolManager){
        this.pool = connectionPoolManager;
    }
    public void save(PasswordToken passwordToken) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  passwordtoken (`id`, `token`,`expirationtime`) VALUES (?,?,?)");
        stat.setLong(1,passwordToken.getIdUser());
        stat.setString(2, passwordToken.getToken());
        stat.setTimestamp(3,new java.sql.Timestamp(passwordToken.getExpirationTime().getTime()));
        stat.executeUpdate();
        connection.close();
    }

    public PasswordToken findUserByToken(String token) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("Select * FROM passwordtoken where token= ?");
        stat.setString(1, token);
        ResultSet res = stat.executeQuery();
        while (res.next()) {
            PasswordToken passwordToken = new PasswordToken(res.getInt(1),res.getString(2),new java.util.Date(res.getTimestamp(3).getTime()));
            return passwordToken;
        }
        connection.close();
        return null;


    }

    public void deletePasswordToken(String token) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("DELETE FROM passwordtoken where token = ?");
        stat.setString(1,token);
        stat.executeUpdate();
        connection.close();
    }
}
