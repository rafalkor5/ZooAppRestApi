package pl.korman.exercise.zooapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.korman.exercise.zooapp.model.dto.AnimalInputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalOutputDTO;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;
import pl.korman.exercise.zooapp.model.mapper.AnimalMapper;
import pl.korman.exercise.zooapp.repository.AnimalRepository;
import pl.korman.exercise.zooapp.repository.AnimalZoneRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final AnimalZoneRepository animalZoneRepository;

    /**
     * > If the animal zone exists, create a new animal and save it to the database.
     * If animal is saved return it as AnimalOutputDTO
     *
     * @param animalInputDTO This is the object that is passed to the method.
     * @return AnimalOutputDTO
     */
    public AnimalOutputDTO addAnimalWithAnimalZone(AnimalInputDTO animalInputDTO) {
        AnimalZone animalZone = animalZoneRepository.findAnimalZoneById(animalInputDTO.getAnimalZoneId());
        if (Objects.isNull(animalZone)) {
            log.info("Animal zone with id {} is no exist. Cannot add animal without correct animalZone id.", animalInputDTO.getAnimalZoneId());
            return null;
        } else {
            return AnimalMapper.INSTANCE
                    .createAnimalOutputDTO(animalRepository.save(AnimalMapper.INSTANCE.createAnimalFromDTO(animalInputDTO, animalZone)));
        }
    }

    /**
     * > We're getting a list of animals from the repository,
     * mapping them to a list of AnimalOutputDTOs, and returning it.
     *
     * @param animalName The name of the animal you want to search for.
     * @return A list of AnimalOutputDTOs
     */
    public List<AnimalOutputDTO> getAnimalsByAnimalName(String animalName) {
        return animalRepository.findAnimalByAnimalName(animalName)
                .stream()
                .map(AnimalMapper.INSTANCE::createAnimalOutputDTO)
                .toList();
    }

    /**
     * > We're getting all the animals from the database, mapping them to DTOs, and returning them.
     *
     * @return A list of AnimalOutputDTOs
     */
    public List<AnimalOutputDTO> getAnimals() {
        return animalRepository.findAll()
                .stream()
                .map(AnimalMapper.INSTANCE::createAnimalOutputDTO)
                .toList();
    }

    /**
     * > We're getting a list of animals by zone id, and then we're mapping them to a list of animal output DTOs.
     *
     * @param id The id of the zone you want to get the animals from.
     * @return A list of AnimalOutputDTOs
     */
    public List<AnimalOutputDTO> getAnimalsByZoneId(Long id) {
        return animalRepository.findAnimalsByAnimalZoneId(id)
                .stream()
                .map(AnimalMapper.INSTANCE::createAnimalOutputDTO)
                .toList();
    }

    /**
     * If the animal exists, delete it and return true.
     * In the opposite case return false.
     *
     * @param id the id of the animal to be deleted
     * @return A boolean value.
     */
    @Transactional
    public boolean deleteAnimalById(long id) {
        if (animalRepository.existsAnimalById(id)) {
            animalRepository.deleteAnimalById(id);
            return true;
        }
        return false;
    }
}
