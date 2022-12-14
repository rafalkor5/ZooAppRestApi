package pl.korman.exercise.zooapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;
import pl.korman.exercise.zooapp.model.mapper.AnimalZoneMapper;
import pl.korman.exercise.zooapp.repository.AnimalRepository;
import pl.korman.exercise.zooapp.repository.AnimalZoneRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AnimalZoneService {

    private final AnimalZoneRepository animalZoneRepository;
    private final AnimalRepository animalRepository;

    /**
     * > It creates a new AnimalZone object, saves it to the database, and returns a DTO object with the new AnimalZone's
     * information
     *
     * @param animalZoneName The name of the animal zone to be created.
     * @return AnimalZoneOutputDTO
     */
    public AnimalZoneOutputDTO addAnimalZone(String animalZoneName) {
        return AnimalZoneMapper.INSTANCE
                .createAnimalZoneOutputDTO(animalZoneRepository.save(new AnimalZone(animalZoneName)));
    }

    /**
     * > We're getting all the animal zones from the database, mapping them to DTOs, and then returning them with
     * list of animals in zones.
     *
     * @return A list of AnimalZoneOutputDTOs
     */
    public List<AnimalZoneOutputDTO> getAnimalZonesWithAnimalsInZone() {
        return animalZoneRepository.findAll()
                .stream()
                .map(animalZone -> {
                    List<Animal> animalsInZone = animalRepository.findAnimalsByAnimalZoneId(animalZone.getId());
                    return AnimalZoneMapper.INSTANCE.createAnimalZoneOutputDTO(animalZone, animalsInZone);
                }).toList();
    }

    /**
     * > We're getting animalZoneById from the database, mapping them to DTOs, and then returning them with
     * list of animals in zones.
     *
     * @param id The id of the animal zone to be retrieved.
     * @return AnimalZoneOutputDTO
     */
    public AnimalZoneOutputDTO getAnimalZoneByIdWithAnimalsInZone(Long id) {
        AnimalZone animalZone = animalZoneRepository.findAnimalZoneById(id);
        if (Objects.nonNull(animalZone)) {
            return AnimalZoneMapper.INSTANCE
                    .createAnimalZoneOutputDTO(animalZone, animalRepository.findAnimalsByAnimalZoneId(animalZone.getId()));
        }
        return null;
    }

    /**
     * > We're getting animalZoneByZoneName from the database, mapping them to DTOs, and then returning them with
     * list of animals in zones.
     *
     * @param zoneName The name of the zone you want to get.
     * @return AnimalZoneOutputDTO
     */
    public AnimalZoneOutputDTO getAnimalZoneByZoneNameWithAnimalsInZone(String zoneName) {
        AnimalZone animalZone = animalZoneRepository.findAnimalZoneByZoneName(zoneName);
        if (Objects.nonNull(animalZone)) {
            return AnimalZoneMapper.INSTANCE
                    .createAnimalZoneOutputDTO(animalZone, animalRepository.findAnimalsByAnimalZoneId(animalZone.getId()));
        }
        return null;
    }

    /**
     * > Find the animal zone with the least number of animals
     *
     * @return AnimalZoneOutputDTO
     */
    public AnimalZoneOutputDTO getZoneWithTheLeastNumberOfAnimalsWithAnimalsInZone() {
        return animalZoneRepository.findAll()
                .stream()
                .min(Comparator.comparing(animalZone -> animalRepository.findAnimalsByAnimalZoneId(animalZone.getId()).size()))
                .map(animalZone -> AnimalZoneMapper.INSTANCE
                        .createAnimalZoneOutputDTO(animalZone, animalRepository.findAnimalsByAnimalZoneId(animalZone.getId())))
                .orElse(null);
    }

    /**
     * If the animal zone exists, delete it
     * In the opposite case return false.
     *
     * @param id the id of the animal zone to be deleted
     * @return A boolean value.
     */
    @Transactional
    public boolean deleteAnimalZoneByIdWithAnimalsInZone(long id) {
        if (animalZoneRepository.existsAnimalZoneById(id)) {
            animalZoneRepository.deleteAnimalZoneById(id);
            return true;
        }
        return false;
    }

}
