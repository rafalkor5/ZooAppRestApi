package pl.korman.exercise.zooapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.korman.exercise.zooapp.model.entity.Animal;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAnimalsByAnimalZoneId(Long id);

    List<Animal> findAnimalByAnimalName(String animalName);

    void deleteAnimalById(Long id);

    boolean existsAnimalById(Long id);


}
