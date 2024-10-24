package ma.zyn.app.unit.dao.facade.core.car;

import ma.zyn.app.bean.core.car.Car;
import ma.zyn.app.dao.facade.core.car.CarDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.time.LocalDateTime;

import ma.zyn.app.bean.core.owner.Owner ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CarDaoTest {

@Autowired
    private CarDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByLicensePlate(){
        String licensePlate = "licensePlate-1";
        Car entity = new Car();
        entity.setLicensePlate(licensePlate);
        underTest.save(entity);
        Car loaded = underTest.findByLicensePlate(licensePlate);
        assertThat(loaded.getLicensePlate()).isEqualTo(licensePlate);
    }

    @Test
    void shouldDeleteByLicensePlate() {
        String licensePlate = "licensePlate-12345678";
        int result = underTest.deleteByLicensePlate(licensePlate);

        Car loaded = underTest.findByLicensePlate(licensePlate);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Car entity = new Car();
        entity.setId(id);
        underTest.save(entity);
        Car loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Car entity = new Car();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Car loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Car> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Car> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Car given = constructSample(1);
        Car saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
