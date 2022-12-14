package pl.korman.exercise.zooapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import pl.korman.exercise.zooapp.model.entity.AnimalZone;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AnimalZoneRepositoryTest {
    public static final long FIRST_ZONE_ID = 1L;
    private static final long FIRST_ANIMAL_ID = 1L;
    private static final String TEST_ANIMAL_ZONE_NAME = "TestAnimalZoneName";

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AnimalZoneRepository animalZoneRepository;

    @Test
    void findAnimalZoneByIdShouldReturnEmptyList() {
        //given
        //when
        var returnFromRepository = animalZoneRepository.findAnimalZoneById(FIRST_ZONE_ID);
        //then
        assertNull(returnFromRepository);
    }

    @Test
    void findAnimalZoneByZoneNameShouldReturnEmptyList() {
        //given
        //when
        var returnFromRepository = animalZoneRepository.findAnimalZoneByZoneName(TEST_ANIMAL_ZONE_NAME);
        //then
        assertNull(returnFromRepository);
    }

    @Test
    void existsAnimalZoneByIdShouldReturnFalse() {
        //given
        //when
        //then
        assertFalse(animalZoneRepository.existsAnimalZoneById(FIRST_ANIMAL_ID));
    }

    @Test
    void existsAnimalZoneByIdShouldReturnTrue() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        //when
        //then
        assertTrue(animalZoneRepository.existsAnimalZoneById(savedAnimalZone.getId()));
    }

    @Test
    void findAnimalZoneByIdShouldReturnEntity() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        //when
        var returnFromRepository = animalZoneRepository.findAnimalZoneById(FIRST_ZONE_ID);
        //then
        assertNotNull(returnFromRepository);
        assertEquals(savedAnimalZone.getId(), returnFromRepository.getId());
        assertEquals(savedAnimalZone.getZoneName(), returnFromRepository.getZoneName());
    }

    @Test
    void findAnimalZoneByZoneNameShouldReturnEntity() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        //when
        var returnFromRepository = animalZoneRepository.findAnimalZoneByZoneName(TEST_ANIMAL_ZONE_NAME);
        //then
        assertNotNull(returnFromRepository);
        assertEquals(savedAnimalZone.getId(), returnFromRepository.getId());
        assertEquals(savedAnimalZone.getZoneName(), returnFromRepository.getZoneName());
    }

    @Test
    void deleteAnimalByIdShouldRepositoryDoNotFindThisAnimal() {
        //given
        var savedAnimalZone = entityManager.persist(new AnimalZone(TEST_ANIMAL_ZONE_NAME));
        //when
        animalZoneRepository.deleteAnimalZoneById(savedAnimalZone.getId());
        //then
        assertFalse(animalZoneRepository.existsAnimalZoneById(savedAnimalZone.getId()));
    }

}