package pl.korman.exercise.zooapp.model.dto;

import lombok.Data;
import pl.korman.exercise.zooapp.model.entity.AnimalType;

@Data
public class AnimalOutputDTO {
    private Long animalId;
    private String animalName;
    private AnimalType animalType;
    private String animalZoneName;
    private Long animalZoneId;
}
