package SportRecap.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Exercice {

    private Long id;

    private String categorie;

    private int currentWeight;

    private String muscle;

    private String description;


    public Exercice(String categorie, int currentWeight, String muscle, String description) {
        this.categorie = categorie;
        this.currentWeight = currentWeight;
        this.muscle = muscle;
        this.description = description;
    }
}
