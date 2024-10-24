package ma.zyn.app.unit.service.impl.admin.owner;

import ma.zyn.app.bean.core.owner.Owner;
import ma.zyn.app.dao.facade.core.owner.OwnerDao;
import ma.zyn.app.service.impl.admin.owner.OwnerAdminServiceImpl;

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
class OwnerAdminServiceImplTest {

    @Mock
    private OwnerDao repository;
    private AutoCloseable autoCloseable;
    private OwnerAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new OwnerAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllOwner() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveOwner() {
        // Given
        Owner toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteOwner() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetOwnerById() {
        // Given
        Long idToRetrieve = 1L; // Example Owner ID to retrieve
        Owner expected = new Owner(); // You need to replace Owner with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Owner result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Owner constructSample(int i) {
		Owner given = new Owner();
        given.setFirstName("firstName-"+i);
        given.setLastName("lastName-"+i);
        given.setPhoneNumber("phoneNumber-"+i);
        given.setPasswordChanged(false);
        given.setAccountNonLocked(false);
        given.setPassword("password-"+i);
        given.setEmail("email-"+i);
        given.setEnabled(false);
        given.setCredentialsNonExpired(false);
        given.setAccountNonExpired(false);
        given.setUsername("username-"+i);
        return given;
    }

}
