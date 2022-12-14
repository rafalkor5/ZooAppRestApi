package pl.korman.exercise.zooapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputWithFoodUsageDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalType;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;
import pl.korman.exercise.zooapp.repository.AnimalRepository;
import pl.korman.exercise.zooapp.repository.AnimalZoneRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalZoneFoodServiceTest {
    private static final Long FIRST_ANIMAL_ZONE_ID = 1L;
    private static final Long SECOND_ANIMAL_ZONE_ID = 2L;
    private static final Long THIRD_ANIMAL_ZONE_ID = 3L;

    @Mock
    private AnimalZone animalZone1;
    @Mock
    private AnimalZone animalZone2;
    @Mock
    private AnimalZone animalZone3;
    @Mock
    private Animal animalElephant;
    @Mock
    private Animal animalTiger;
    @Mock
    private Animal animalRabbit;

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private AnimalZoneRepository animalZoneRepository;
    @InjectMocks
    private AnimalZoneFoodService animalZoneFoodService;

    @Test
    void getZoneWithBiggestUsageOfPetFoodShouldReturnAnimalZone() {
        //given
        final int biggestFoodUsage = 40;

        List<AnimalZone> animalZoneList = new ArrayList<>(Arrays.asList(animalZone3, animalZone1, animalZone2));
        when(animalZoneRepository.findAll()).thenReturn(animalZoneList);
        when(animalZone1.getId()).thenReturn(FIRST_ANIMAL_ZONE_ID);
        when(animalZone2.getId()).thenReturn(SECOND_ANIMAL_ZONE_ID);
        when(animalZone3.getId()).thenReturn(THIRD_ANIMAL_ZONE_ID);


        List<Animal> animalListSize1 = new ArrayList<>(Collections.singletonList(animalTiger));
        List<Animal> animalListSize2 = new ArrayList<>(Arrays.asList(animalElephant, animalElephant));
        List<Animal> animalListSize3 = new ArrayList<>(Arrays.asList(animalRabbit, animalRabbit, animalRabbit));

        when(animalTiger.getAnimalType()).thenReturn(AnimalType.TIGER);
        when(animalElephant.getAnimalType()).thenReturn(AnimalType.ELEPHANT);
        when(animalRabbit.getAnimalType()).thenReturn(AnimalType.RABBIT);

        when(animalRepository.findAnimalsByAnimalZoneId(FIRST_ANIMAL_ZONE_ID)).thenReturn(animalListSize1);
        when(animalRepository.findAnimalsByAnimalZoneId(SECOND_ANIMAL_ZONE_ID)).thenReturn(animalListSize2);
        when(animalRepository.findAnimalsByAnimalZoneId(THIRD_ANIMAL_ZONE_ID)).thenReturn(animalListSize3);

        //when
        AnimalZoneOutputWithFoodUsageDTO outputFromService = animalZoneFoodService.getZoneWithBiggestUsageOfPetFoodAndWithAnimalsInZone();
        //then
        assertNotNull(outputFromService);
        assertEquals(biggestFoodUsage, outputFromService.getPetFoodUsage());
    }
}