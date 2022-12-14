package pl.korman.exercise.zooapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import pl.korman.exercise.zooapp.model.entity.Animal;
import pl.korman.exercise.zooapp.model.entity.AnimalType;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AnimalRepositoryTest {
    public static final long FIRST_ZONE_ID = 1L;
    public static final String TEST_ANIMAL_NAME = "TestAnimalName";
    private static final long FIRST_ANIMAL_ID = 1L;
    private static final AnimalType ANIMAL_TYPE = AnimalType.ELEPHANT;
    private static final String TEST_ANIMAL_ZONE_NAME = "TestAnimalZoneName";

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AnimalRepository animalRepository;

    @Test
    void findAnimalsByAnimalZoneIdShouldReturnEmptyList() {
        //given
        //when
        var animals = animalRepository.findAnimalsByAnimalZoneId(FIRST_ZONE_ID);
        //then
        assertTrue(animals.isEmpty());
    }

    @Test
    void findAnimalByAnimalNameShouldReturnEmptyList() {
        //given
        //when
        var animals = animalRepository.findAnimalByAnimalName(TEST_ANIMAL_NAME);
        //then
        assertTrue(animals.isEmpty());
    }

    @Test
    void existsAnimalByIdShouldReturnFalse() {
        //given
        //when
        //then
        assertFalse(animalRepository.existsAnimalById(FIRST_ANIMAL_ID));
    }

    @Test
    void existsAnimalByIdShouldReturnTrue() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        var savedAnimal = entityManager.persist(new Animal(TEST_ANIMAL_NAME, ANIMAL_TYPE, savedAnimalZone));
        //when
        //then
        assertTrue(animalRepository.existsAnimalById(savedAnimal.getId()));
    }

    @Test
    void findAnimalsByAnimalZoneIdShouldReturnNotEmptyList() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        entityManager.persist(new Animal(TEST_ANIMAL_NAME, ANIMAL_TYPE, savedAnimalZone));
        //when
        var animals = animalRepository.findAnimalsByAnimalZoneId(FIRST_ZONE_ID);
        //then
        assertFalse(animals.isEmpty());
        assertEquals(1, animals.size());
        assertEquals(TEST_ANIMAL_NAME, animals.get(0).getAnimalName());
        assertEquals(ANIMAL_TYPE, animals.get(0).getAnimalType());
        assertEquals(savedAnimalZone, animals.get(0).getAnimalZone());
    }

    @Test
    void findAnimalByAnimalNameShouldReturnNotEmptyList() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        entityManager.persist(new Animal(TEST_ANIMAL_NAME, ANIMAL_TYPE, savedAnimalZone));
        //when
        var animals = animalRepository.findAnimalByAnimalName(TEST_ANIMAL_NAME);
        //then
        assertFalse(animals.isEmpty());
        assertEquals(1, animals.size());
        assertEquals(TEST_ANIMAL_NAME, animals.get(0).getAnimalName());
        assertEquals(ANIMAL_TYPE, animals.get(0).getAnimalType());
        assertEquals(savedAnimalZone, animals.get(0).getAnimalZone());
    }

    @Test
    void deleteAnimalByIdShouldRepositoryDoNotFindThisAnimal() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        var savedAnimal = entityManager.persist(new Animal(TEST_ANIMAL_NAME, ANIMAL_TYPE, savedAnimalZone));
        //when
        animalRepository.deleteAnimalById(savedAnimal.getId());
        //then
        assertFalse(animalRepository.existsAnimalById(savedAnimal.getId()));
    }
}