package SportRecap.DAO;


import SportRecap.model.Exercice;
import SportRecap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ExerciceRepository {



    private final ConnectionPoolManager pool;

    @Autowired
    public ExerciceRepository(ConnectionPoolManager connectionPoolManager){
        this.pool = connectionPoolManager;
    }

    public void addUserExo(int user_id,Exercice exercice) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  exercice (`id_user`,`name`, `category`,`muscle`,`description`,`lastweight`) VALUES (?,?,?,?,?)");
        stat.setInt(1,user_id);
        stat.setString(2, exercice.getName());
        stat.setString(3, exercice.getCategorie());
        if(exercice.getMuscle()!=null) stat.setString(4,exercice.getMuscle());
        else stat.setString(5,"null");
        stat.setString(6,exercice.getDescription());
        stat.setInt(7,exercice.getCurrentWeight());
        stat.executeUpdate();
        connection.close();
    }
     public Exercice getExoFromList(int id_exo) throws SQLException {

         int id = 0;
         String name = null;
         String categorie = null;
         String muscle = null;
         String description = null;

         Connection connection = this.pool.getConnection();
         PreparedStatement stat = connection.prepareStatement("SELECT * FROM exerciceliste WHERE id = ?");
         stat.setInt(1, id_exo);
         ResultSet res = stat.executeQuery();

         while (res.next()) {
             name = res.getString(1);
             categorie = res.getString(2);
             muscle = res.getString(3);
             description = res.getString(4);
         }
         connection.close();
         if(id == 0){
             return null;
         }else {
             Exercice exercice = new Exercice(name,categorie,muscle,description);
             return exercice;
         }
     }


    public Collection<Exercice> getUserExo(int userId) throws SQLException {
        int id = 0;
        String name = null;
        String categorie = null;
        String muscle = null;
        String description = null;
        Map<Date,Integer> history = new HashMap<>();

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM exercice WHERE id_user = ?");
        stat.setInt(1, userId);
        ResultSet res = stat.executeQuery();

        List<Exercice> exercices= new ArrayList<>();

        while (res.next()) {
            id = userId;
            name = res.getString(1);
            categorie = res.getString(2);
            muscle = res.getString(3);
            description = res.getString(4);
            history = this.gethistory(userId);
            Exercice exercice = new Exercice(name,categorie,muscle,description,history);
            exercices.add(exercice);
        }
        connection.close();
        if(id == 0){
            return null;
        }else {
            return exercices;
        }
    }

    private Map<Date,Integer> gethistory(int exoId) throws SQLException {
        Map<Date,Integer> history = new HashMap<>();

        int id = 0;
        Date date = null;
        int weight = 0;

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM exo_history WHERE exoId = ?");
        stat.setInt(1, exoId);
        ResultSet res = stat.executeQuery();

        List<Exercice> exercices= new ArrayList<>();

        while (res.next()) {
            id = exoId;
            weight = res.getInt(1);
            date = res.getDate(2);
            history.put(date,weight);
        }
        connection.close();
        if(id == 0){
            return null;
        }else {
            return history;
        }
    }


}
