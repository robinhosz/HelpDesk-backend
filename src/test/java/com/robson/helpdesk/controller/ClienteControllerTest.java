package com.robson.helpdesk.controller;

import com.robson.helpdesk.dto.ClienteDTO;
import com.robson.helpdesk.enums.Perfil;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.service.ClienteService;
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
class ClienteControllerTest {

    public static final String NOME = "Robson";
    public static final String EMAIL = "robson@mail.com";
    public static final String CPF = "063.550.699-84";
    public static final String SENHA = "123";
    public static final Set<Perfil> PERFIL = new HashSet<>();
    public static final LocalDate DATA = LocalDate.now();
    public static final Integer ID = 1;
    public static final int INDEX = 0;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ModelMapper mapper;

    Cliente cliente;
    ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(clienteService.findById(anyInt())).thenReturn(cliente);
        when(mapper.map(any(), any())).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteDTO.class, response.getBody().getClass());

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
        when(clienteService.findAll()).thenReturn(List.of(cliente));
        when(mapper.map(any(), any())).thenReturn(clienteDTO);

        ResponseEntity<List<ClienteDTO>> response = clienteController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ClienteDTO.class, response.getBody().get(INDEX).getClass());

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
        when(clienteService.create(any())).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = clienteController.create(clienteDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(clienteService.update(clienteDTO)).thenReturn(cliente);
        when(mapper.map(any(), any())).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.update(ID, clienteDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ClienteDTO.class, response.getBody().getClass());

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
        doNothing().when(clienteService).delete((anyInt()));

        ResponseEntity<ClienteDTO> response = clienteController.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).delete(anyInt());

    }

    private void startCliente() {
        cliente = new Cliente(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
        clienteDTO = new ClienteDTO(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
    }
}
