package pl.korman.exercise.zooapp.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @NotNull
    private String animalName;

    @Enumerated(EnumType.STRING)
    private AnimalType animalType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "animal_zone_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AnimalZone animalZone;

    public Animal(String animalName, AnimalType animalType, AnimalZone animalZone) {
        this.animalName = animalName;
        this.animalType = animalType;
        this.animalZone = animalZone;
    }
}
