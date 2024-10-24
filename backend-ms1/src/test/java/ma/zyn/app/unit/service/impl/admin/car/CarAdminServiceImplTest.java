package ma.zyn.app.unit.service.impl.admin.car;

import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.dao.facade.core.car.CarDao;
import ma.zyn.app.service.impl.admin.car.CarAdminServiceImpl;

import ma.zyn.app.bean.core.owner.Owner ;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;



import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class CarAdminServiceImplTest {

    @Mock
    private CarDao repository;
    private AutoCloseable autoCloseable;
    private CarAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CarAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCar() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCar() {
        // Given
        Car toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCar() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetCarById() {
        // Given
        Long idToRetrieve = 1L; // Example Car ID to retrieve
        Car expected = new Car(); // You need to replace Car with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Car result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Car constructSample(int i) {
		Car given = new Car();
        given.setBrand("brand-"+i);
        given.setModel("model-"+i);
        given.setYear(i);
        given.setColor("color-"+i);
        given.setLicensePlate("licensePlate-"+i);
        given.setOwner(new Owner(1L));
        return given;
    }

}
