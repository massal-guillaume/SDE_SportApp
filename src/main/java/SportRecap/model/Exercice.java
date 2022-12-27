package SportRecap.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Exercice {

    private Long uuid;

    private String categorie;

    private int currentWeight;

    private String description;

    private List<Integer> WeightHistoric;




}
