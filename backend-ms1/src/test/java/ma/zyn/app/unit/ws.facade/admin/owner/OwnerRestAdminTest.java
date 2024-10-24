package ma.zyn.app.unit.ws.facade.admin.owner;

import ma.zyn.app.bean.core.owner.Owner;
import ma.zyn.app.service.impl.admin.owner.OwnerAdminServiceImpl;
import ma.zyn.app.ws.facade.admin.owner.OwnerRestAdmin;
import ma.zyn.app.ws.converter.owner.OwnerConverter;
import ma.zyn.app.ws.dto.owner.OwnerDto;
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
public class OwnerRestAdminTest {

    private MockMvc mockMvc;

    @Mock
    private OwnerAdminServiceImpl service;
    @Mock
    private OwnerConverter converter;

    @InjectMocks
    private OwnerRestAdmin controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllOwnerTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<OwnerDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<OwnerDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveOwnerTest() throws Exception {
        // Mock data
        OwnerDto requestDto = new OwnerDto();
        Owner entity = new Owner();
        Owner saved = new Owner();
        OwnerDto savedDto = new OwnerDto();

        // Mock the converter to return the owner object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved owner DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<OwnerDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        OwnerDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved owner DTO
        assertEquals(savedDto.getFirstName(), responseBody.getFirstName());
        assertEquals(savedDto.getLastName(), responseBody.getLastName());
        assertEquals(savedDto.getPhoneNumber(), responseBody.getPhoneNumber());
        assertEquals(savedDto.getPasswordChanged(), responseBody.getPasswordChanged());
        assertEquals(savedDto.getAccountNonLocked(), responseBody.getAccountNonLocked());
        assertEquals(savedDto.getPassword(), responseBody.getPassword());
        assertEquals(savedDto.getEmail(), responseBody.getEmail());
        assertEquals(savedDto.getEnabled(), responseBody.getEnabled());
        assertEquals(savedDto.getCredentialsNonExpired(), responseBody.getCredentialsNonExpired());
        assertEquals(savedDto.getAccountNonExpired(), responseBody.getAccountNonExpired());
        assertEquals(savedDto.getUsername(), responseBody.getUsername());
    }

}
