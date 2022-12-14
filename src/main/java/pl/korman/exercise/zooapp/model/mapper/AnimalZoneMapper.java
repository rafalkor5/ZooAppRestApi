package pl.korman.exercise.zooapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputWithFoodUsageDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

import java.util.List;

@Mapper
public interface AnimalZoneMapper {
    AnimalZoneMapper INSTANCE = Mappers.getMapper(AnimalZoneMapper.class);

    @Mapping(source = "animalZone.id", target = "id")
    @Mapping(source = "animalZone.zoneName", target = "zoneName")
    @Mapping(source = "animalsInZone", target = "animals")
    @Mapping(expression = "java(animalsInZone!= null ? animalsInZone.size():0)", target = "numberOfAnimalsInZone")
    @Mapping(source = "foodUsage", target = "petFoodUsage")
    AnimalZoneOutputWithFoodUsageDTO createAnimalZoneOutputDTO(AnimalZone animalZone, List<Animal> animalsInZone, int foodUsage);

    @Mapping(source = "animalZone.id", target = "id")
    @Mapping(source = "animalZone.zoneName", target = "zoneName")
    @Mapping(source = "animalsInZone", target = "animals")
    @Mapping(expression = "java(animalsInZone!= null ? animalsInZone.size():0)", target = "numberOfAnimalsInZone")
    AnimalZoneOutputDTO createAnimalZoneOutputDTO(AnimalZone animalZone, List<Animal> animalsInZone);

    @Mapping(source = "animalZone.id", target = "id")
    @Mapping(source = "animalZone.zoneName", target = "zoneName")
    AnimalZoneOutputDTO createAnimalZoneOutputDTO(AnimalZone animalZone);
}
