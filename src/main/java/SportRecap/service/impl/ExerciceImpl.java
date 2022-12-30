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
import java.util.Collection;

@Service
public class ExerciceImpl implements ExerciceService {


    private ExerciceRepository exerciceRepository;

    private ExternalExerciceGrabber externalExerciceGrabber;
    @Autowired
    public ExerciceImpl(ExerciceRepository exerciceRepository,ExternalExerciceGrabber externalExerciceGrabber){
        this.exerciceRepository = exerciceRepository;
        this.externalExerciceGrabber = externalExerciceGrabber;
    }

    @Override
    public Collection<Exercice> listExercice(int id) {
       return null;
    }

    @Override
    public String getToken() throws JSONException, IOException {
        return this.externalExerciceGrabber.getToken();
    }

    @Override
    public void getExercice() throws IOException {
        this.externalExerciceGrabber.getExercice();
    }

}
