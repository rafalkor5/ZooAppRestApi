package pl.korman.exercise.zooapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.korman.exercise.zooapp.model.dto.AnimalInputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalOutputDTO;
import pl.korman.exercise.zooapp.service.AnimalService;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AnimalControllerTest {
    private static final long FIRST_ANIMAL_ID = 1L;
    private static final String TEST_ANIMAL_NAME = "TestAnimalName";
    private static final long FIRST_ZONE_ID = 1L;

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalController animalController;

    @Mock
    private AnimalOutputDTO animalOutputDTO;
    @Mock
    private AnimalInputDTO animalInputDTO;

    @Test
    void createAnimalShouldReturnStatusCodeCreatedAndCorrectUri() {
        //given
        when(animalService.addAnimalWithAnimalZone(any())).thenReturn(animalOutputDTO);
        when(animalOutputDTO.getAnimalId()).thenReturn(FIRST_ANIMAL_ID);
        //when
        var response = animalController.createAnimal(animalInputDTO);
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/1", response.getHeaders().getLocation().toString());
    }

    @Test
    void createAnimalWhenReturnFromServiceIsNullShouldReturnBadRequestStatusCode() {
        //given
        //when
        var response = animalController.createAnimal(animalInputDTO);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteAnimalWithCorrectIdShouldReturnNoContentStatusCode() {
        //given
        when(animalService.deleteAnimalById(anyLong())).thenReturn(true);
        //when
        var response = animalController.deleteAnimal(FIRST_ANIMAL_ID);
        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteAnimalWithIdDoNotExitsShouldReturnBadRequestStatusCode() {
        //given
        //when
        var response = animalController.deleteAnimal(FIRST_ANIMAL_ID);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void getAnimalsShouldReturnNotEmptyList() {
        //given
        when(animalService.getAnimals()).thenReturn(new ArrayList<>(Collections.singletonList(animalOutputDTO)));
        //when
        var response = animalController.getAnimals();
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getAnimalsShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalController.getAnimals();
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAnimalsByAnimalByNameShouldReturnStatusCodeOk() {
        //given
        when(animalService.getAnimalsByAnimalName(anyString())).thenReturn(new ArrayList<>(Collections.singletonList(animalOutputDTO)));
        //when
        var response = animalController.getAnimalsByAnimalName(TEST_ANIMAL_NAME);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get(0));
    }

    @Test
    void getAnimalsByAnimalByNameWithNameDoNotExitsShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalController.getAnimalsByAnimalName(TEST_ANIMAL_NAME);
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAnimalsByZoneByIdWithIdDoNotExitsShouldReturnStatusCodeNotFound() {
        //given
        when(animalService.getAnimalsByZoneId(anyLong())).thenReturn(new ArrayList<>(Collections.singletonList(animalOutputDTO)));
        //when
        var response = animalController.getAnimalsByZoneId(FIRST_ZONE_ID);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().get(0));
    }

    @Test
    void getAnimalsByZoneByIdShouldReturnStatusCodeOk() {
        //given
        //when
        var response = animalController.getAnimalsByZoneId(FIRST_ZONE_ID);
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}