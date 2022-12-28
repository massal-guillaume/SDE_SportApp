package SportRecap.DAO;


import SportRecap.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class VerifTokenRepository {

    private final ConnectionPoolManager pool;

    @Autowired
    public VerifTokenRepository(ConnectionPoolManager connectionPoolManager){
        this.pool = connectionPoolManager;
    }
    public void save(VerificationToken veriftoken) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  veriftoken (`id`, `token`,`expirationtime`) VALUES (?,?,?)");
        stat.setLong(1,veriftoken.getIdUser());
        stat.setString(2, veriftoken.getToken());
        stat.setTimestamp(3,new java.sql.Timestamp(veriftoken.getExpirationTime().getTime()));
        stat.executeUpdate();
        connection.close();
    }

    public VerificationToken findByToken(String token) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("Select * FROM veriftoken where token= ?");
        stat.setString(1, token);
        ResultSet res = stat.executeQuery();
        while (res.next()) {
        VerificationToken verificationToken = new VerificationToken(res.getInt(1),res.getString(2),new java.util.Date(res.getTimestamp(3).getTime()));
        return verificationToken;
        }
        connection.close();
        return null;
    }

    public void delete(VerificationToken verificationToken) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("DELETE FROM veriftoken where token = ?");
        stat.setString(1, verificationToken.getToken());
        stat.executeUpdate();
        connection.close();
    }

    public VerificationToken getVerifTokenById(long id) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("Select * FROM veriftoken where id= ?");
        stat.setLong(1, id);
        ResultSet res = stat.executeQuery();
        while (res.next()) {
            VerificationToken verificationToken = new VerificationToken(res.getInt(1),res.getString(2),new java.util.Date(res.getTimestamp(3).getTime()));
            return verificationToken;
        }
        connection.close();
        return null;
    }
}
