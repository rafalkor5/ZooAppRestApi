package pl.korman.exercise.zooapp.model.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.korman.exercise.zooapp.model.dto.AnimalInputDTO;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalType;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalMapperTest {
    private static final Long ANIMAL_ID = 1L;
    private static final String TEST_ANIMAL_NAME = "TestAnimalName";
    private static final AnimalType ANIMAL_TYPE = AnimalType.ELEPHANT;
    private static final Long ANIMAL_ZONE_ID = 1L;
    private static final String TEST_ANIMAL_ZONE_NAME = "TestAnimalZoneName";

    @Mock
    private AnimalInputDTO animalInputDTO;
    @Mock
    private AnimalZone animalZone;
    @Mock
    private Animal animal;

    @Test
    void createAnimalOutputDtoShouldReturnEqualsFields() {
        //given
        when(animal.getId()).thenReturn(ANIMAL_ID);
        when(animal.getAnimalType()).thenReturn(ANIMAL_TYPE);
        when(animal.getAnimalName()).thenReturn(TEST_ANIMAL_NAME);
        when(animal.getAnimalZone()).thenReturn(animalZone);
        when(animalZone.getId()).thenReturn(ANIMAL_ZONE_ID);
        when(animalZone.getZoneName()).thenReturn(TEST_ANIMAL_ZONE_NAME);
        //when
        var returnFromMapper = AnimalMapper.INSTANCE.createAnimalOutputDTO(animal);
        //then
        assertEquals(ANIMAL_ID, returnFromMapper.getAnimalId());
        assertEquals(ANIMAL_TYPE, returnFromMapper.getAnimalType());
        assertEquals(TEST_ANIMAL_NAME, returnFromMapper.getAnimalName());
        assertEquals(ANIMAL_ZONE_ID, returnFromMapper.getAnimalZoneId());
        assertEquals(TEST_ANIMAL_ZONE_NAME, returnFromMapper.getAnimalZoneName());
    }

    @Test
    void createAnimalFromDTOShouldReturnEqualsFields() {
        //given
        when(animalInputDTO.getAnimalType()).thenReturn(ANIMAL_TYPE);
        when(animalInputDTO.getAnimalName()).thenReturn(TEST_ANIMAL_NAME);
        //when
        var returnFromMapper = AnimalMapper.INSTANCE.createAnimalFromDTO(animalInputDTO, animalZone);
        //then
        assertNull(returnFromMapper.getId());
        assertEquals(ANIMAL_TYPE, returnFromMapper.getAnimalType());
        assertEquals(TEST_ANIMAL_NAME, returnFromMapper.getAnimalName());
        assertEquals(animalZone, returnFromMapper.getAnimalZone());
    }
}