package pl.korman.exercise.zooapp.model.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalZoneMapperTest {
    private static final Long ANIMAL_ZONE_ID = 1L;
    private static final String TEST_ANIMAL_ZONE_NAME = "TestAnimalZoneName";

    @Mock
    private AnimalZone animalZone;
    @Mock
    private Animal animal;

    @Test
    void createAnimalZoneOutputDTOShouldReturnEqualsFields1() {
        //given
        when(animalZone.getId()).thenReturn(ANIMAL_ZONE_ID);
        when(animalZone.getZoneName()).thenReturn(TEST_ANIMAL_ZONE_NAME);
        //when
        var returnFromMapper = AnimalZoneMapper.INSTANCE.createAnimalZoneOutputDTO(animalZone);
        //then
        assertEquals(ANIMAL_ZONE_ID, returnFromMapper.getId());
        assertEquals(TEST_ANIMAL_ZONE_NAME, returnFromMapper.getZoneName());
        assertNull(returnFromMapper.getAnimals());
    }

    @Test
    void createAnimalZoneOutputDTOShouldReturnEqualsFields2() {
        //given
        final List<Animal> animalList = new ArrayList<>(List.of(animal));
        final int sizeOfAnimalList = animalList.size();
        when(animalZone.getId()).thenReturn(ANIMAL_ZONE_ID);
        when(animalZone.getZoneName()).thenReturn(TEST_ANIMAL_ZONE_NAME);
        //when
        var returnFromMapper = AnimalZoneMapper.INSTANCE.createAnimalZoneOutputDTO(animalZone, animalList);
        //then
        assertEquals(ANIMAL_ZONE_ID, returnFromMapper.getId());
        assertEquals(TEST_ANIMAL_ZONE_NAME, returnFromMapper.getZoneName());
        assertNotNull(returnFromMapper.getAnimals());
        assertEquals(animal, returnFromMapper.getAnimals().get(0));
        assertEquals(sizeOfAnimalList, returnFromMapper.getNumberOfAnimalsInZone());
    }

    @Test
    void createAnimalZoneOutputDTOShouldReturnEqualsFields3() {
        //given
        final List<Animal> animalList = new ArrayList<>(List.of(animal));
        final int sizeOfAnimalList = animalList.size();
        final int petFoodUsage = 50;

        when(animalZone.getId()).thenReturn(ANIMAL_ZONE_ID);
        when(animalZone.getZoneName()).thenReturn(TEST_ANIMAL_ZONE_NAME);
        //when
        var returnFromMapper = AnimalZoneMapper.INSTANCE.createAnimalZoneOutputDTO(animalZone, animalList, petFoodUsage);
        //then
        assertEquals(ANIMAL_ZONE_ID, returnFromMapper.getId());
        assertEquals(TEST_ANIMAL_ZONE_NAME, returnFromMapper.getZoneName());
        assertNotNull(returnFromMapper.getAnimals());
        assertEquals(animal, returnFromMapper.getAnimals().get(0));
        assertEquals(sizeOfAnimalList, returnFromMapper.getNumberOfAnimalsInZone());
        assertEquals(petFoodUsage, returnFromMapper.getPetFoodUsage());
    }

}