package pl.korman.exercise.zooapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

@Repository
public interface AnimalZoneRepository extends JpaRepository<AnimalZone, Long> {

    AnimalZone findAnimalZoneByZoneName(String zoneName);

    AnimalZone findAnimalZoneById(Long zoneId);

    void deleteAnimalZoneById(Long id);

    boolean existsAnimalZoneById(Long id);
}
