package SportRecap.service.impl;

import SportRecap.DAO.ExerciceRepository;
import SportRecap.DAO.ExternalExerciceGrabber;
import SportRecap.model.Exercice;
import SportRecap.model.User;
import SportRecap.service.ExerciceService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class ExerciceImpl implements ExerciceService {


    private final ExerciceRepository exerciceRepository;

    private final ExternalExerciceGrabber externalExerciceGrabber;
    @Autowired
    public ExerciceImpl(ExerciceRepository exerciceRepository,ExternalExerciceGrabber externalExerciceGrabber){
        this.exerciceRepository = exerciceRepository;
        this.externalExerciceGrabber = externalExerciceGrabber;
    }

    @Override
    public Collection<Exercice> listExercice() throws SQLException {
       return this.exerciceRepository.getAllExoFromList();
    }

    @Override
    public String getToken() throws JSONException, IOException {
        return this.externalExerciceGrabber.getToken();
    }

    @Override
    public void saveExercice(User user , int exo_idFromList) throws SQLException {
        Exercice exercice = this.exerciceRepository.getExoFromList(exo_idFromList);
        this.exerciceRepository.addUserExo(user.getId(),exercice);
    }

    @Override
    public Collection<Exercice> getUserExercice(User usernamefromrequest) throws SQLException {
        return this.exerciceRepository.getUserExo(usernamefromrequest.getId());
    }

    @Override
    public void addNewCharge(int exoId, int weight) throws SQLException {
        this.exerciceRepository.updateExo(exoId,weight);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        this.exerciceRepository.addWeightInHistory(exoId,weight,date);
    }
    @Override
    public void grabExercice() throws IOException, SQLException, JSONException {
        this.externalExerciceGrabber.grabExercice();
    }
}
