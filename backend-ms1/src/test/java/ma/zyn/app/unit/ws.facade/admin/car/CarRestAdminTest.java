package ma.zyn.app.unit.ws.facade.admin.car;

import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.service.impl.admin.car.CarAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.car.CarRestAdmin;
import ma.zyn.app.ws.converter.car.CarConverter;
import ma.zyn.app.ws.dto.car.CarDto;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private CarAdminServiceImpl service;
    @Mock
    private CarConverter converter;

    @InjectMocks
    private CarRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllCarTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<CarDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<CarDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveCarTest() throws Exception {
        // Mock data
        CarDto requestDto = new CarDto();
        Car entity = new Car();
        Car saved = new Car();
        CarDto savedDto = new CarDto();

        // Mock the converter to return the car object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved car DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<CarDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        CarDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved car DTO
        assertEquals(savedDto.getBrand(), responseBody.getBrand());
        assertEquals(savedDto.getModel(), responseBody.getModel());
        assertEquals(savedDto.getYear(), responseBody.getYear());
        assertEquals(savedDto.getColor(), responseBody.getColor());
        assertEquals(savedDto.getLicensePlate(), responseBody.getLicensePlate());
    }

}
