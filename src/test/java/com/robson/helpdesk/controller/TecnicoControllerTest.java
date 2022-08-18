package com.robson.helpdesk.controller;

import com.robson.helpdesk.dto.TecnicoDTO;
import com.robson.helpdesk.enums.Perfil;
import com.robson.helpdesk.model.Tecnico;
import com.robson.helpdesk.service.TecnicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class TecnicoControllerTest {

    public static final String NOME = "Robson";
    public static final String EMAIL = "robson@mail.com";
    public static final String CPF = "063.550.699-84";
    public static final String SENHA = "123";
    public static final Set<Perfil> PERFIL = new HashSet<>();
    public static final LocalDate DATA = LocalDate.now();
    public static final Integer ID = 1;
    public static final int INDEX = 0;
    @InjectMocks
    private TecnicoController tecnicoController;

    @Mock
    private TecnicoService tecnicoService;

    @Mock
    private ModelMapper mapper;

    Tecnico tecnico;
    TecnicoDTO tecnicoDTO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        startTecnico();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(tecnicoService.findById(anyInt())).thenReturn(tecnico);
        when(mapper.map(any(), any())).thenReturn(tecnicoDTO);

        ResponseEntity<TecnicoDTO> response = tecnicoController.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TecnicoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(SENHA, response.getBody().getSenha());
        assertEquals(DATA, response.getBody().getDataCriacao());
        assertEquals(PERFIL, response.getBody().getPerfis());
    }

    @Test
    void whenFindAllThenReturnLitOfTecnicoDTO() {
        when(tecnicoService.findAll()).thenReturn(List.of(tecnico));
        when(mapper.map(any(), any())).thenReturn(tecnicoDTO);

        ResponseEntity<List<TecnicoDTO>> response = tecnicoController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(TecnicoDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NOME, response.getBody().get(INDEX).getNome());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(CPF, response.getBody().get(INDEX).getCpf());
        assertEquals(SENHA, response.getBody().get(INDEX).getSenha());
        assertEquals(DATA, response.getBody().get(INDEX).getDataCriacao());
        assertEquals(PERFIL, response.getBody().get(INDEX).getPerfis());

    }

    @Test
    void whenCreateThenReturnCreated() {
        when(tecnicoService.create(any())).thenReturn(tecnico);

        ResponseEntity<TecnicoDTO> response = tecnicoController.create(tecnicoDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(tecnicoService.update(tecnicoDTO)).thenReturn(tecnico);
        when(mapper.map(any(), any())).thenReturn(tecnicoDTO);

        ResponseEntity<TecnicoDTO> response = tecnicoController.update(ID, tecnicoDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(TecnicoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(SENHA, response.getBody().getSenha());
        assertEquals(DATA, response.getBody().getDataCriacao());
        assertEquals(PERFIL, response.getBody().getPerfis());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(tecnicoService).delete((anyInt()));

        ResponseEntity<TecnicoDTO> response = tecnicoController.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tecnicoService, times(1)).delete(anyInt());

    }

    private void startTecnico() {
        tecnico = new Tecnico(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
        tecnicoDTO = new TecnicoDTO(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
    }
}