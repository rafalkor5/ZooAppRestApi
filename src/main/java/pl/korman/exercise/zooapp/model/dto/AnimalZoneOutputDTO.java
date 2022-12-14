package pl.korman.exercise.zooapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.korman.exercise.zooapp.model.entity.Animal;

import java.util.List;

@Data
@AllArgsConstructor
public class AnimalZoneOutputDTO {
    private Long id;
    private String zoneName;
    private int numberOfAnimalsInZone;
    private List<Animal> animals;
}
