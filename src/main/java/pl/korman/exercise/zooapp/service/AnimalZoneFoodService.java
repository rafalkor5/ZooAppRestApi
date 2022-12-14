package pl.korman.exercise.zooapp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputWithFoodUsageDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.mapper.AnimalZoneMapper;
import pl.korman.exercise.zooapp.repository.AnimalRepository;
import pl.korman.exercise.zooapp.repository.AnimalZoneRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalZoneFoodService {
    private final AnimalZoneRepository animalZoneRepository;
    private final AnimalRepository animalRepository;

    private static final int ELEPHANT_FOOD_USAGE = 20;
    private static final int TIGER_FOOD_USAGE = 11;
    private static final int RABBIT_FOOD_USAGE = 4;


    /**
     * We get all the animal zones from the database, then we get all the animals from each zone, then we calculate the
     * usage of food per zone, then we get and return the zone with the biggest usage of food.
     *
     * @return AnimalZoneOutputWithFoodUsageDTO
     */
    public AnimalZoneOutputWithFoodUsageDTO getZoneWithBiggestUsageOfPetFoodAndWithAnimalsInZone() {
        var mapWithAnimalZoneAndSetOfAnimals =
                animalZoneRepository.findAll().stream()
                        .collect(Collectors.toMap(animalZone -> animalZone, animalZone -> animalRepository.findAnimalsByAnimalZoneId(animalZone.getId())));
        var mapWithAnimalZoneAndUsageFoodPerZone =
                mapWithAnimalZoneAndSetOfAnimals.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, x -> usageFoodPerZone(x.getValue())));

        return mapWithAnimalZoneAndUsageFoodPerZone.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(animalZoneIntegerEntry -> AnimalZoneMapper.INSTANCE.createAnimalZoneOutputDTO(
                        animalZoneIntegerEntry.getKey(),
                        animalRepository.findAnimalsByAnimalZoneId(animalZoneIntegerEntry.getKey().getId()),
                        animalZoneIntegerEntry.getValue()))
                .orElse(null);
    }

    /**
     * > It takes a list of animals, and returns the sum of the food usage of each animal in the list
     *
     * @param animalSet a list of animals
     * @return The sum of the food usage of all animals in the animalSet.
     */
    private int usageFoodPerZone(List<Animal> animalSet) {
        return animalSet.stream().map(this::usageFoodPerAnimal).toList().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * "Return the food usage for the given animal."
     * <p>
     * The switch expression is used to determine the food usage for the given animal
     *
     * @param animal The animal object that we want to calculate the food usage for.
     * @return The return type is int.
     */
    private int usageFoodPerAnimal(Animal animal) {
        return switch (animal.getAnimalType()) {
            case ELEPHANT -> ELEPHANT_FOOD_USAGE;
            case TIGER -> TIGER_FOOD_USAGE;
            case RABBIT -> RABBIT_FOOD_USAGE;
        };
    }
}
