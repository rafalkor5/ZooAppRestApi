package pl.korman.exercise.zooapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;
import pl.korman.exercise.zooapp.repository.AnimalRepository;
import pl.korman.exercise.zooapp.repository.AnimalZoneRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalZoneServiceTest {
    private static final Long FIRST_ANIMAL_ZONE_ID = 1L;
    private static final Long SECOND_ANIMAL_ZONE_ID = 2L;
    private static final Long THIRD_ANIMAL_ZONE_ID = 3L;
    private final static String ANIMAL_ZONE_NAME = "TestZone";

    @Mock
    private AnimalZone animalZone1;
    @Mock
    private AnimalZone animalZone2;
    @Mock
    private AnimalZone animalZone3;
    @Mock
    private Animal animal;

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private AnimalZoneRepository animalZoneRepository;
    @InjectMocks
    private AnimalZoneService animalZoneService;

    @Test
    void addAnimalZoneShouldInvokeSaveOnRepository() {
        //given
        //when
        animalZoneService.addAnimalZone(ANIMAL_ZONE_NAME);
        //then
        verify(animalZoneRepository).save(any());
    }

    @Test
    void getAnimalZonesShouldReturnListWithAnimals() {
        //given
        List<AnimalZone> animalZoneList = new ArrayList<>(Collections.singletonList(animalZone1));
        when(animalZoneRepository.findAll()).thenReturn(animalZoneList);
        when(animalZone1.getId()).thenReturn(FIRST_ANIMAL_ZONE_ID);

        List<Animal> animalList = new ArrayList<>(Collections.singletonList(animal));
        when(animalRepository.findAnimalsByAnimalZoneId(anyLong())).thenReturn(animalList);

        //when
        List<AnimalZoneOutputDTO> outputFromService = animalZoneService.getAnimalZonesWithAnimalsInZone();
        //then
        assertNotNull(outputFromService.get(0));
        assertEquals(1, outputFromService.get(0).getAnimals().size());
        verify(animalZoneRepository).findAll();
        verify(animalRepository).findAnimalsByAnimalZoneId(FIRST_ANIMAL_ZONE_ID);
    }

    @Test
    void getAnimalZoneByIdShouldReturnListWithAnimals() {
        //given
        when(animalZoneRepository.findAnimalZoneById(FIRST_ANIMAL_ZONE_ID)).thenReturn(animalZone1);
        when(animalZone1.getId()).thenReturn(FIRST_ANIMAL_ZONE_ID);

        List<Animal> animalList = new ArrayList<>(Collections.singletonList(animal));
        when(animalRepository.findAnimalsByAnimalZoneId(anyLong())).thenReturn(animalList);

        //when
        AnimalZoneOutputDTO outputFromService = animalZoneService.getAnimalZoneByIdWithAnimalsInZone(FIRST_ANIMAL_ZONE_ID);
        //then
        assertNotNull(outputFromService);
        assertEquals(1, outputFromService.getAnimals().size());
        verify(animalZoneRepository).findAnimalZoneById(FIRST_ANIMAL_ZONE_ID);
        verify(animalRepository).findAnimalsByAnimalZoneId(FIRST_ANIMAL_ZONE_ID);
    }

    @Test
    void getAnimalZoneByIdShouldReturnNullWhenAnimalZoneDoNotExist() {
        //given
        //when
        //then
        assertNull(animalZoneService.getAnimalZoneByIdWithAnimalsInZone(FIRST_ANIMAL_ZONE_ID));
        verify(animalZoneRepository).findAnimalZoneById(FIRST_ANIMAL_ZONE_ID);
        verify(animalRepository, never()).findAnimalsByAnimalZoneId(anyLong());
    }

    @Test
    void getAnimalZoneByZoneNameShouldReturnListWithAnimals() {
        //given
        when(animalZoneRepository.findAnimalZoneByZoneName(ANIMAL_ZONE_NAME)).thenReturn(animalZone1);
        when(animalZone1.getId()).thenReturn(FIRST_ANIMAL_ZONE_ID);

        List<Animal> animalList = new ArrayList<>(Collections.singletonList(animal));
        when(animalRepository.findAnimalsByAnimalZoneId(anyLong())).thenReturn(animalList);

        //when
        AnimalZoneOutputDTO outputFromService = animalZoneService.getAnimalZoneByZoneNameWithAnimalsInZone(ANIMAL_ZONE_NAME);
        //then
        assertNotNull(outputFromService);
        assertEquals(1, outputFromService.getAnimals().size());
        verify(animalZoneRepository).findAnimalZoneByZoneName(ANIMAL_ZONE_NAME);
        verify(animalRepository).findAnimalsByAnimalZoneId(FIRST_ANIMAL_ZONE_ID);
    }

    @Test
    void getAnimalZoneByZoneNameShouldReturnNullWhenAnimalZoneDoNotExist() {
        //given
        //when
        //then
        assertNull(animalZoneService.getAnimalZoneByZoneNameWithAnimalsInZone(ANIMAL_ZONE_NAME));
        verify(animalZoneRepository).findAnimalZoneByZoneName(ANIMAL_ZONE_NAME);
        verify(animalRepository, never()).findAnimalsByAnimalZoneId(anyLong());
    }

    @Test
    void getZoneWithTheLeastNumberOfAnimalsShouldReturnTheLeastNumberOfAnimalsInAnimalZone() {
        //given

        List<AnimalZone> animalZoneList = new ArrayList<>(Arrays.asList(animalZone3, animalZone1, animalZone2));
        when(animalZoneRepository.findAll()).thenReturn(animalZoneList);
        when(animalZone1.getId()).thenReturn(FIRST_ANIMAL_ZONE_ID);
        when(animalZone2.getId()).thenReturn(SECOND_ANIMAL_ZONE_ID);
        when(animalZone3.getId()).thenReturn(THIRD_ANIMAL_ZONE_ID);


        List<Animal> animalListSize1 = new ArrayList<>(Collections.singletonList(animal));
        List<Animal> animalListSize2 = new ArrayList<>(Arrays.asList(animal, animal));
        List<Animal> animalListSize3 = new ArrayList<>(Arrays.asList(animal, animal, animal));
        when(animalRepository.findAnimalsByAnimalZoneId(FIRST_ANIMAL_ZONE_ID)).thenReturn(animalListSize1);
        when(animalRepository.findAnimalsByAnimalZoneId(SECOND_ANIMAL_ZONE_ID)).thenReturn(animalListSize2);
        when(animalRepository.findAnimalsByAnimalZoneId(THIRD_ANIMAL_ZONE_ID)).thenReturn(animalListSize3);

        //when
        AnimalZoneOutputDTO outputFromService = animalZoneService.getZoneWithTheLeastNumberOfAnimalsWithAnimalsInZone();
        //then
        assertNotNull(outputFromService);
        assertEquals(1, outputFromService.getAnimals().size());
        verify(animalZoneRepository).findAll();
        verify(animalRepository, times(5)).findAnimalsByAnimalZoneId(anyLong());
    }


    @Test
    void deleteAnimalZoneByIdShouldReturnTrue() {
        //given
        when(animalZoneRepository.existsAnimalZoneById(FIRST_ANIMAL_ZONE_ID)).thenReturn(true);
        //when
        //then
        assertTrue(animalZoneService.deleteAnimalZoneByIdWithAnimalsInZone(FIRST_ANIMAL_ZONE_ID));
        verify(animalZoneRepository).existsAnimalZoneById(FIRST_ANIMAL_ZONE_ID);
        verify(animalZoneRepository).deleteAnimalZoneById(FIRST_ANIMAL_ZONE_ID);
    }

    @Test
    void deleteAnimalZoneByIdShouldReturnFalseAndNotDeleteAnimal() {
        //given
        when(animalZoneRepository.existsAnimalZoneById(FIRST_ANIMAL_ZONE_ID)).thenReturn(false);
        //when
        //then
        assertFalse(animalZoneService.deleteAnimalZoneByIdWithAnimalsInZone(FIRST_ANIMAL_ZONE_ID));
        verify(animalZoneRepository).existsAnimalZoneById(FIRST_ANIMAL_ZONE_ID);
        verify(animalZoneRepository, never()).deleteAnimalZoneById(FIRST_ANIMAL_ZONE_ID);
    }
}