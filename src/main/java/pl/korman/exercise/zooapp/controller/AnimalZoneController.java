package pl.korman.exercise.zooapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneInputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputWithFoodUsageDTO;
import pl.korman.exercise.zooapp.service.AnimalZoneFoodService;
import pl.korman.exercise.zooapp.service.AnimalZoneService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/animalZone")
public class AnimalZoneController {
    private final AnimalZoneService animalZoneService;
    private final AnimalZoneFoodService animalZoneFoodService;

    /**
     * > The function creates an animal zone and returns the created animal zone with status code 201.
     * If the object was created unsuccessfully return status code 400.
     *
     * @param animalZoneInputDTO This is the input DTO that will be used to create the animal zone.
     * @return ResponseEntity<AnimalZoneOutputDTO>
     */
    @PostMapping()
    public ResponseEntity<AnimalZoneOutputDTO> createAnimalZone(@RequestBody @Valid AnimalZoneInputDTO animalZoneInputDTO) {
        var animalZone = animalZoneService.addAnimalZone(animalZoneInputDTO.getZoneName());
        if (Objects.nonNull(animalZone)) {
            return ResponseEntity.created(URI.create("/" + animalZone.getId())).body(animalZone);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * If the animal zone is deleted, return a 204 status code. If not, return a 400 status code
     *
     * @param animalZoneId The id of the animal zone to be deleted.
     * @return ResponseEntity with empty body and status code.
     */
    @DeleteMapping("/{animalZoneId}")
    public ResponseEntity<?> deleteAnimalZone(@PathVariable @NotNull Long animalZoneId) {
        if (animalZoneService.deleteAnimalZoneByIdWithAnimalsInZone(animalZoneId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * It returns a list of AnimalZoneOutputDTO objects, which are the animal zones with the animals in each zone
     * Otherwise, it returns ResponseEntity with empty body and status code 404.
     *
     * @return A list of AnimalZoneOutputDTO objects in ResponseEntity
     */
    @GetMapping()
    public ResponseEntity<List<AnimalZoneOutputDTO>> getAnimalZonesWithAnimals() {
        var animalZone = animalZoneService.getAnimalZonesWithAnimalsInZone();
        if (!animalZone.isEmpty()) {
            return ResponseEntity.ok(animalZone);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * > This function returns an animal zone with the given id, if it exists.
     * Otherwise, it returns ResponseEntity with empty body and status code 404.
     *
     * @param animalZoneId The id of the animal zone to be retrieved.
     * @return ResponseEntity<AnimalZoneOutputDTO>
     */
    @GetMapping("/{animalZoneId}")
    public ResponseEntity<AnimalZoneOutputDTO> getAnimalZoneByIdWithAnimals(@PathVariable @NotNull Long animalZoneId) {
        var animalZone = animalZoneService.getAnimalZoneByIdWithAnimalsInZone(animalZoneId);
        if (Objects.nonNull(animalZone)) {
            return ResponseEntity.ok(animalZone);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * > This function returns animal zone with given name with list of animals in this zone.
     * Otherwise, it returns ResponseEntity with empty body and status code 404.
     *
     * @param animalZoneName The name of the animal zone.
     * @return ResponseEntity<AnimalZoneOutputDTO>
     */
    @GetMapping("/name/{animalZoneName}")
    public ResponseEntity<AnimalZoneOutputDTO> getAnimalZoneByZoneNameWithAnimals(@PathVariable @NotNull String animalZoneName) {
        var animalZone = animalZoneService.getAnimalZoneByZoneNameWithAnimalsInZone(animalZoneName);
        if (Objects.nonNull(animalZone)) {
            return ResponseEntity.ok(animalZone);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * It returns the zone with the least number of animals in it, along with the animals in that zone
     * Otherwise, it returns ResponseEntity with empty body and status code 404.
     *
     * @return A ResponseEntity object is being returned.
     */
    @GetMapping("/leastNumberOfAnimals")
    public ResponseEntity<AnimalZoneOutputDTO> getZoneWithTheLeastNumberOfAnimalsWithAnimals() {
        var animalZone = animalZoneService.getZoneWithTheLeastNumberOfAnimalsWithAnimalsInZone();
        if (Objects.nonNull(animalZone)) {
            return ResponseEntity.ok(animalZone);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * It returns the zone with the biggest usage of pet food and with animals in the zone.
     * Otherwise, it returns ResponseEntity with empty body and status code 404.
     *
     * @return AnimalZoneOutputWithFoodUsageDTO
     */
    @GetMapping("/biggestUsageOfPetFood")
    public ResponseEntity<AnimalZoneOutputWithFoodUsageDTO> getZoneWithBiggestUsageOfPetFoodWithAnimals() {
        var animalZone = animalZoneFoodService.getZoneWithBiggestUsageOfPetFoodAndWithAnimalsInZone();
        if (Objects.nonNull(animalZone)) {
            return ResponseEntity.ok(animalZone);
        }
        return ResponseEntity.notFound().build();
    }
}
