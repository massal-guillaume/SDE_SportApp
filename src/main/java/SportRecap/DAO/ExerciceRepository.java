package SportRecap.DAO;


import SportRecap.model.Exercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class ExerciceRepository {



    private final ConnectionPoolManager pool;

    @Autowired
    public ExerciceRepository(ConnectionPoolManager connectionPoolManager){
        this.pool = connectionPoolManager;
    }
/*
    public Collection<Exercice> getAllExercice(int id) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM user WHERE id= ?");
        stat.setInt(1, id);
        ResultSet res = stat.executeQuery();
        while (res.next()) {

        }

    }

 */
}
