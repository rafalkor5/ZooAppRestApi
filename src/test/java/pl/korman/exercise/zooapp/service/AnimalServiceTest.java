package pl.korman.exercise.zooapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.korman.exercise.zooapp.model.dto.AnimalInputDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalType;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;
import pl.korman.exercise.zooapp.repository.AnimalRepository;
import pl.korman.exercise.zooapp.repository.AnimalZoneRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {
    private final static String ANIMAL_NAME = "TestAnimal";
    private final static String ANIMAL_ZONE_NAME = "TestZone";
    private final static AnimalType ANIMAL_TYPE = AnimalType.ELEPHANT;
    @Mock
    private AnimalZone animalZone;
    @Mock
    private Animal animal;

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private AnimalZoneRepository animalZoneRepository;
    @InjectMocks
    private AnimalService animalService;

    @Test
    void addAnimalWithAnimalZoneIdNotExitShouldReturnNull() {
        //given
        Long animalId = 1L;
        AnimalInputDTO animalInputDTO = new AnimalInputDTO(ANIMAL_NAME, AnimalType.ELEPHANT, animalId);
        //when
        //then
        assertNull(animalService.addAnimalWithAnimalZone(animalInputDTO));
        verify(animalRepository, never()).save(any());
    }

    @Test
    void addAnimalWithCorrectAnimalZoneIdShouldReturnAnimalDto() {
        //given
        final Long animalId = 1L;
        final Long animalZoneId = 1L;
        AnimalInputDTO animalInputDTO = new AnimalInputDTO(ANIMAL_NAME, ANIMAL_TYPE, animalId);

        when(animalZoneRepository.findAnimalZoneById(animalZoneId)).thenReturn(animalZone);
        when(animalZone.getZoneName()).thenReturn(ANIMAL_ZONE_NAME);
        when(animalZone.getId()).thenReturn(animalZoneId);

        when(animalRepository.save(any())).thenReturn(animal);
        when(animal.getId()).thenReturn(animalId);
        when(animal.getAnimalType()).thenReturn(ANIMAL_TYPE);
        when(animal.getAnimalZone()).thenReturn(animalZone);
        //when
        //then
        assertNotNull(animalService.addAnimalWithAnimalZone(animalInputDTO));
        verify(animalRepository).save(any());
    }

    @Test
    void getAnimalsByAnimalNameShouldReturnListOfAnimalsInDtoSizeIsEquals2() {
        //given
        final Long firstAnimalId = 1L;
        final Long secondAnimalId = 2L;
        final Long animalZoneId = 1L;
        when(animalZone.getId()).thenReturn(animalZoneId);
        when(animalZone.getZoneName()).thenReturn(ANIMAL_ZONE_NAME);
        List<Animal> animals = Arrays.asList(animal, animal);
        when(animal.getId()).thenReturn(firstAnimalId).thenReturn(secondAnimalId);
        when(animal.getAnimalName()).thenReturn(ANIMAL_NAME);
        when(animal.getAnimalType()).thenReturn(ANIMAL_TYPE);
        when(animal.getAnimalZone()).thenReturn(animalZone);

        when(animalRepository.findAnimalByAnimalName(ANIMAL_NAME)).thenReturn(animals);
        //when
        //then
        assertEquals(2, animalService.getAnimalsByAnimalName(ANIMAL_NAME).size());
        verify(animalRepository).findAnimalByAnimalName(ANIMAL_NAME);
    }

    @Test
    void getAnimalsShouldReturnListOfAnimalsInDtoSizeIsEquals3() {
        //given
        final Long firstAnimalId = 1L;
        final Long secondAnimalId = 2L;
        final Long thirdAnimalId = 3L;
        final Long animalZoneId = 1L;
        when(animalZone.getId()).thenReturn(animalZoneId);
        when(animalZone.getZoneName()).thenReturn(ANIMAL_ZONE_NAME);
        List<Animal> animals = Arrays.asList(animal, animal, animal);
        when(animal.getId()).thenReturn(firstAnimalId).thenReturn(secondAnimalId).thenReturn(thirdAnimalId);
        when(animal.getAnimalName()).thenReturn(ANIMAL_NAME);
        when(animal.getAnimalType()).thenReturn(ANIMAL_TYPE);
        when(animal.getAnimalZone()).thenReturn(animalZone);

        when(animalRepository.findAll()).thenReturn(animals);
        //when
        //then
        assertEquals(3, animalService.getAnimals().size());
        verify(animalRepository).findAll();
    }

    @Test
    void getAnimalsByZoneIdShouldReturnListOfAnimalsInDtoSizeIsEquals2() {
        //given
        final Long firstAnimalId = 1L;
        final Long secondAnimalId = 2L;
        final Long animalZoneId = 1L;
        when(animalZone.getId()).thenReturn(animalZoneId);
        when(animalZone.getZoneName()).thenReturn(ANIMAL_ZONE_NAME);
        List<Animal> animals = Arrays.asList(animal, animal);
        when(animal.getId()).thenReturn(firstAnimalId).thenReturn(secondAnimalId);
        when(animal.getAnimalName()).thenReturn(ANIMAL_NAME);
        when(animal.getAnimalType()).thenReturn(ANIMAL_TYPE);
        when(animal.getAnimalZone()).thenReturn(animalZone);

        when(animalRepository.findAnimalsByAnimalZoneId(animalZoneId)).thenReturn(animals);
        //when
        //then
        assertEquals(2, animalService.getAnimalsByZoneId(animalZoneId).size());
        verify(animalRepository).findAnimalsByAnimalZoneId(animalZoneId);
    }

    @Test
    void deleteAnimalByIdShouldReturnTrue() {
        //given
        final Long firstAnimalId = 1L;
        when(animalRepository.existsAnimalById(firstAnimalId)).thenReturn(true);
        //when
        //then
        assertTrue(animalService.deleteAnimalById(firstAnimalId));
        verify(animalRepository).existsAnimalById(firstAnimalId);
        verify(animalRepository).deleteAnimalById(firstAnimalId);
    }

    @Test
    void deleteAnimalByIdShouldReturnFalseAndNotDeleteAnimal() {
        //given
        final Long firstAnimalId = 1L;
        when(animalRepository.existsAnimalById(firstAnimalId)).thenReturn(false);
        //when
        //then
        assertFalse(animalService.deleteAnimalById(firstAnimalId));
        verify(animalRepository).existsAnimalById(firstAnimalId);
        verify(animalRepository, never()).deleteAnimalById(firstAnimalId);
    }

}