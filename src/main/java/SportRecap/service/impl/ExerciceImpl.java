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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Collection<Exercice> listExercice(User usernamefromrequest) throws SQLException {
        List<Exercice> liste = this.exerciceRepository.getAllExoFromList();
        List<Exercice> personal_liste = this.exerciceRepository.getUserExo(usernamefromrequest.getId());
        List<Exercice> final_liste = new ArrayList<>();
        if(personal_liste!=null) {
            for (Exercice exercice : liste) {
                boolean present = false;
                for (int y = 0; y < personal_liste.size(); y++) {
                    if (exercice.getName().equals(liste.get(y).getName())) {
                        present = true;
                        break;
                    }
                }
                if (!present) final_liste.add(exercice);
            }
            return final_liste;
        }else  return liste;
    }

    @Override
    public String getToken() throws JSONException, IOException {
        return this.externalExerciceGrabber.getToken();
    }

    @Override
    public boolean saveExercice(User user , int exo_idFromList) throws SQLException {
        Exercice exercice = this.exerciceRepository.getExoFromList(exo_idFromList);
        if(!this.exerciceRepository.checkIfExerciceIsAlreadyRegister(user.getId(), exercice.getName())){
            this.exerciceRepository.addUserExo(user.getId(),exercice);
            return true;
        }else return false;
    }

    @Override
    public Collection<Exercice> getUserExercice(User usernamefromrequest) throws SQLException {
        return this.exerciceRepository.getUserExo(usernamefromrequest.getId());
    }

    @Override
    public void addNewCharge(int exoId,int userId,int weight) throws SQLException {
        this.exerciceRepository.updateExo(exoId,weight);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        this.exerciceRepository.addWeightInHistory(exoId,userId,weight,date);
    }

    @Override
    public Collection<String> getCategory() throws SQLException {
        return this.exerciceRepository.getCategory();
    }

    @Override
    public void grabExercice() throws IOException, SQLException, JSONException {
        this.externalExerciceGrabber.grabCategory();
        this.externalExerciceGrabber.grabMuscles();
        this.externalExerciceGrabber.grabExercice();
    }
}
