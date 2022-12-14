package pl.korman.exercise.zooapp.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.korman.exercise.zooapp.model.dto.AnimalInputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalOutputDTO;
import pl.korman.exercise.zooapp.service.AnimalService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    /**
     * It takes an AnimalInputDTO as input, creates an Animal object from it. If the object was created successfully
     * returns an AnimalOutputDTO in ResponseEntity.
     *
     * @param animalInputDTO This is the object that will be passed in the request body.
     * @return A AnimalOutputDTO in ResponseEntity
     */
    @PostMapping()
    public ResponseEntity<AnimalOutputDTO> createAnimal(@RequestBody @Valid AnimalInputDTO animalInputDTO) {
        var animal = animalService.addAnimalWithAnimalZone(animalInputDTO);
        if (Objects.nonNull(animal)) {
            return ResponseEntity.created(URI.create("/" + animal.getAnimalId())).body(animal);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * If the animal is deleted, return a 204 status code. If not, return a 400 status code
     *
     * @param animalId The id of the animal to be deleted.
     * @return A ResponseEntity object.
     */
    @DeleteMapping("/{animalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable @NotNull Long animalId) {
        if (animalService.deleteAnimalById(animalId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * It returns a list of animals if there are any.
     * Otherwise, it returns ResponseEntity with a status 404.
     *
     * @return A list of AnimalOutputDTO in ResponseEntity
     */
    @GetMapping()
    public ResponseEntity<List<AnimalOutputDTO>> getAnimals() {
        var animals = animalService.getAnimals();
        if (!animals.isEmpty()) {
            return ResponseEntity.ok(animals);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * It returns a list of animals that have the same name as the animal name passed in the path variable.
     * Otherwise, it returns ResponseEntity with a status 404.
     *
     * @param animalName The name of the animal you want to search for.
     * @return A list of AnimalOutputDTOs in ResponseEntity
     */
    @GetMapping("/{animalName}")
    public ResponseEntity<List<AnimalOutputDTO>> getAnimalsByAnimalName(@PathVariable @NotNull String animalName) {
        var animals = animalService.getAnimalsByAnimalName(animalName);
        if (!animals.isEmpty()) {
            return ResponseEntity.ok(animals);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * > This function returns a list of animals that are in a specific zone.
     * Otherwise, it returns ResponseEntity with a status 404.
     *
     * @param zoneId The id of the zone that you want to get the animals from.
     * @return A list of AnimalOutputDTO in ResponseEntity
     */
    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<AnimalOutputDTO>> getAnimalsByZoneId(@PathVariable @NotNull Long zoneId) {
        var animals = animalService.getAnimalsByZoneId(zoneId);
        if (!animals.isEmpty()) {
            return ResponseEntity.ok(animals);
        }
        return ResponseEntity.notFound().build();
    }
}
