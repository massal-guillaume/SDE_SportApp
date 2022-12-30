package SportRecap.service;

import SportRecap.model.Exercice;
import SportRecap.model.User;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

public interface ExerciceService {
    Collection<Exercice> listExercice(int id);

    String getToken() throws JSONException, IOException;

    void getExercice() throws IOException;

}
