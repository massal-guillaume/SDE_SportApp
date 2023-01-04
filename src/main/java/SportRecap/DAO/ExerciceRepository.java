package SportRecap.DAO;


import SportRecap.model.Exercice;
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
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  exercice (`id_user`,`name`, `category`,`muscle`,`description`,`lastweight`) VALUES (?,?,?,?,?,?)");
        stat.setInt(1,user_id);
        stat.setString(2, exercice.getName());
        stat.setString(3, exercice.getCategorie());
        if(exercice.getMuscle()!=null) stat.setString(4,exercice.getMuscle());
        else stat.setString(4,"null");
        stat.setString(5,exercice.getDescription());
        stat.setInt(6,exercice.getCurrentWeight());
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
             id = id_exo;
             name = res.getString(2);
             categorie = res.getString(3);
             muscle = res.getString(4);
             description = res.getString(5);
         }
         connection.close();
         if(id == 0){
             return null;
         }else {
             return new Exercice(name,categorie,muscle,description);
         }
     }


    public Collection<Exercice> getUserExo(int userId) throws SQLException {

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM exercice WHERE id_user = ?");
        stat.setInt(1, userId);
        ResultSet res = stat.executeQuery();

        List<Exercice> exercices= new ArrayList<>();
        int id = 0;

        while (res.next()) {
           id = res.getInt(1);
           String name = res.getString(3);
           String categorie = res.getString(4);
           String  muscle = res.getString(5);
           String  description = res.getString(6);
           Map<Date,Integer> history = this.gethistory(userId);
           Exercice exercice = new Exercice(id,name,categorie,muscle,description,history);
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

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM history WHERE id_exo = ?");
        stat.setInt(1, exoId);
        ResultSet res = stat.executeQuery();


        while (res.next()) {
            id = exoId;
            int weight = res.getInt(1);
            Date date = res.getDate(2);
            history.put(date,weight);
        }
        connection.close();
        if(id == 0){
            return null;
        }else {
            return history;
        }
    }


    public void updateExo(int exoId, int weight) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("UPDATE exercice SET  lastweight=? WHERE idexercice=?");
        stat.setInt(1,weight);
        stat.setInt(2,exoId);
        stat.executeUpdate();
        connection.close();
    }

    public void addWeightInHistory(int exoId, int weight,Date date) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("INSERT INTO  history (`id_exo`,`weight`, `date`) VALUES (?,?,?)");
        stat.setInt(1,exoId);
        stat.setInt(2, weight);
        stat.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
        stat.executeUpdate();
        connection.close();
    }

    public Collection<Exercice> getAllExoFromList() throws SQLException {
        List<Exercice> exercices = new ArrayList<>();

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM exerciceliste");
        ResultSet res = stat.executeQuery();

        while (res.next()) {
            int id = res.getInt(1);
            String name = res.getString(2);
            String categorie = res.getString(3);
            String muscle = res.getString(4);
            String description = res.getString(5);
            Exercice exercice = new Exercice(id,name,categorie,muscle,description,null);
            exercices.add(exercice);
        }
        connection.close();
            return exercices;
        }

    public Collection<String> getCategory() throws SQLException {
        List<String> category = new ArrayList<>();

        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM category");
        ResultSet res = stat.executeQuery();

        while (res.next()) {;
            category.add(res.getString(2));
        }
        connection.close();
        return category;
    }

    public boolean checkIfExerciceIsAlreadyRegister(int id,String name) throws SQLException {
        Connection connection = this.pool.getConnection();
        PreparedStatement stat = connection.prepareStatement("SELECT * FROM exercice where name=? AND id_user=?");
        stat.setString(1,name);
        stat.setInt(2,id);
        ResultSet res = stat.executeQuery();

        while (res.next()) {;
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }
}
