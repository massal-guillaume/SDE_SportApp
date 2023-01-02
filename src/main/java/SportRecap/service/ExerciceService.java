package SportRecap.service;

import SportRecap.model.Exercice;
import SportRecap.model.User;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface ExerciceService {
    Collection<Exercice> listExercice() throws SQLException;

    String getToken() throws JSONException, IOException;

    void  grabExercice() throws JSONException,IOException, SQLException;
    void saveExercice(User user , int exo_id) throws JSONException, IOException, SQLException;

    Collection<Exercice> getUserExercice(User usernamefromrequest) throws SQLException;

    void addNewCharge(int exoId, int weight) throws SQLException;
}
