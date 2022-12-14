package pl.korman.exercise.zooapp.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.korman.exercise.zooapp.model.dto.AnimalInputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalOutputDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

@Mapper
public interface AnimalMapper {
    AnimalMapper INSTANCE = Mappers.getMapper(AnimalMapper.class);

    @Mapping(source = "id", target = "animalId")
    @Mapping(source = "animalName", target = "animalName")
    @Mapping(source = "animalType", target = "animalType")
    @Mapping(source = "animalZone.zoneName", target = "animalZoneName")
    @Mapping(source = "animalZone.id", target = "animalZoneId")
    AnimalOutputDTO createAnimalOutputDTO(Animal animal);

    @Mapping(source = "animalInputDTO.animalName", target = "animalName")
    @Mapping(source = "animalInputDTO.animalType", target = "animalType")
    @Mapping(source = "animalZone", target = "animalZone")
    Animal createAnimalFromDTO(AnimalInputDTO animalInputDTO, AnimalZone animalZone);


}
