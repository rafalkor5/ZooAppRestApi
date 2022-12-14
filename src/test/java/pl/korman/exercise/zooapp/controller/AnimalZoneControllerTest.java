package pl.korman.exercise.zooapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneInputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputDTO;
import pl.korman.exercise.zooapp.model.dto.AnimalZoneOutputWithFoodUsageDTO;
import pl.korman.exercise.zooapp.service.AnimalZoneFoodService;
import pl.korman.exercise.zooapp.service.AnimalZoneService;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalZoneControllerTest {
    public static final String TEST_ANIMAL_ZONE_NAME = "TestAnimalZoneName";
    public static final long FIRST_ZONE_ID = 1L;

    @Mock
    private AnimalZoneInputDTO animalZoneInputDTO;
    @Mock
    private AnimalZoneOutputDTO animalZoneOutputDTO;
    @Mock
    private AnimalZoneOutputWithFoodUsageDTO animalZoneOutputWithFoodUsageDTO;

    @Mock
    private AnimalZoneService animalZoneService;
    @Mock
    private AnimalZoneFoodService animalZoneFoodService;
    @InjectMocks
    private AnimalZoneController animalZoneController;

    @Test
    void createAnimalZoneShouldReturnStatusCodeCreatedAndCorrectUri() {
        //given
        when(animalZoneService.addAnimalZone(any())).thenReturn(animalZoneOutputDTO);
        when(animalZoneOutputDTO.getId()).thenReturn(FIRST_ZONE_ID);
        //when
        var response = animalZoneController.createAnimalZone(animalZoneInputDTO);
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/1", response.getHeaders().getLocation().toString());
    }

    @Test
    void createAnimalZoneWhenReturnFromServiceIsNullShouldReturnBadRequestStatusCode() {
        //given
        //when
        var response = animalZoneController.createAnimalZone(animalZoneInputDTO);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteAnimalZoneWithIdDoNotExitsShouldReturnBadRequestStatusCode() {
        //given
        //when
        var response = animalZoneController.deleteAnimalZone(FIRST_ZONE_ID);
        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteAnimalZoneWithCorrectIdShouldReturnNoContentStatusCode() {
        //given
        when(animalZoneService.deleteAnimalZoneByIdWithAnimalsInZone(anyLong())).thenReturn(true);
        //when
        var response = animalZoneController.deleteAnimalZone(FIRST_ZONE_ID);
        //then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getAnimalsShouldReturnNotEmptyList() {
        //given
        when(animalZoneService.getAnimalZonesWithAnimalsInZone()).thenReturn(new ArrayList<>(Collections.singletonList(animalZoneOutputDTO)));
        //when
        var response = animalZoneController.getAnimalZonesWithAnimals();
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getAnimalsShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalZoneController.getAnimalZonesWithAnimals();
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAnimalsByZoneIdShouldReturnAnimalZoneOutputDto() {
        //given
        when(animalZoneService.getAnimalZoneByIdWithAnimalsInZone(anyLong())).thenReturn(animalZoneOutputDTO);
        when(animalZoneOutputDTO.getId()).thenReturn(FIRST_ZONE_ID);
        //when
        var response = animalZoneController.getAnimalZoneByIdWithAnimals(FIRST_ZONE_ID);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getAnimalsByZoneIdShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalZoneController.getAnimalZoneByIdWithAnimals(FIRST_ZONE_ID);
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAnimalsByAnimalByNameShouldReturnStatusCodeOk() {
        //given
        when(animalZoneService.getAnimalZoneByZoneNameWithAnimalsInZone(anyString())).thenReturn(animalZoneOutputDTO);
        when(animalZoneOutputDTO.getZoneName()).thenReturn(TEST_ANIMAL_ZONE_NAME);
        //when
        var response = animalZoneController.getAnimalZoneByZoneNameWithAnimals(TEST_ANIMAL_ZONE_NAME);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(TEST_ANIMAL_ZONE_NAME, response.getBody().getZoneName());
    }


    @Test
    void getAnimalsByAnimalZoneByNameWithNameDoNotExitsShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalZoneController.getAnimalZoneByZoneNameWithAnimals(TEST_ANIMAL_ZONE_NAME);
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getZoneWithTheLeastNumberOfAnimalsShouldReturnAnimalZoneOutputDTO() {
        //given
        when(animalZoneService.getZoneWithTheLeastNumberOfAnimalsWithAnimalsInZone()).thenReturn(animalZoneOutputDTO);
        //when
        var response = animalZoneController.getZoneWithTheLeastNumberOfAnimalsWithAnimals();
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getZoneWithTheLeastNumberOfAnimalsWithEmptyRepositoryShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalZoneController.getZoneWithTheLeastNumberOfAnimalsWithAnimals();
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getZoneWithBiggestUsageOfPetFoodReturnAnimalZoneOutputDTO() {
        //given
        when(animalZoneFoodService.getZoneWithBiggestUsageOfPetFoodAndWithAnimalsInZone()).thenReturn(animalZoneOutputWithFoodUsageDTO);
        //when
        var response = animalZoneController.getZoneWithBiggestUsageOfPetFoodWithAnimals();
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(animalZoneOutputWithFoodUsageDTO.getClass(), response.getBody().getClass());
    }

    @Test
    void getZoneWithBiggestUsageOfPetFoodWithEmptyRepositoryShouldReturnStatusCodeNotFound() {
        //given
        //when
        var response = animalZoneController.getZoneWithBiggestUsageOfPetFoodWithAnimals();
        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}