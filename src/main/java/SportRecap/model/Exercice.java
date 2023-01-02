package SportRecap.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class Exercice {

    private int id;

    private String name;
    private String categorie;

    private int currentWeight;

    private String muscle;

    private String description;

    private Map<Date,Integer> history;


    public Exercice(String name,String categorie,String muscle, String description) {
        this.name = name;
        this.categorie = categorie;
        this.muscle = muscle;
        this.description = description;
    }

    public Exercice(int id, String name, String categorie, String muscle, String description, Map<Date,Integer> history) {
        this.id = id;
        this.name = name;
        this.categorie = categorie;
        this.muscle = muscle;
        this.description = description;
        this.history = history;
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categorie='" + categorie + '\'' +
                ", currentWeight=" + currentWeight +
                ", muscle='" + muscle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
