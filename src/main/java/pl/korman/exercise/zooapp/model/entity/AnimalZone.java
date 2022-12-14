package pl.korman.exercise.zooapp.model.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "animal_zones")
@Data
@NoArgsConstructor
public class AnimalZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @NotNull
    @Column(unique = true, length = 40)
    private String zoneName;

    public AnimalZone(String zoneName) {
        this.zoneName = zoneName;
    }
}
