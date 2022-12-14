package pl.korman.exercise.zooapp.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import pl.korman.exercise.zooapp.model.entity.AnimalType;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
public class AnimalInputDTO {
    @NotNull
    private String animalName;
    @NotNull
    private AnimalType animalType;
    @NotNull
    private Long animalZoneId;
}
