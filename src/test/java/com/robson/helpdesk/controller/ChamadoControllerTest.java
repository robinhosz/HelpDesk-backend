package com.robson.helpdesk.controller;

import com.robson.helpdesk.dto.ChamadoDTO;
import com.robson.helpdesk.enums.Prioridade;
import com.robson.helpdesk.enums.Status;
import com.robson.helpdesk.model.Chamado;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.model.Tecnico;
import com.robson.helpdesk.service.ChamadoService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChamadoControllerTest {

    public static final LocalDate DATA_ABERTURA = LocalDate.now();
    public static final LocalDate DATA_FECHAMENTO = LocalDate.now();
    public static final Prioridade PRIORIDADE = Prioridade.ALTA;
    public static final Status STATUS = Status.ABERTO;
    public static final LocalDate DATA = LocalDate.now();
    public static final String TITULO = "Chamado";
    public static final String OBS = "Observe";
    public static final String NOME_TECNICO = "Jose";
    public static final String NOME_CLIENTE = "Robson";
    public static final Tecnico TECNICO = new Tecnico();
    public static final Cliente CLIENTE = new Cliente();

    public static final Integer ID = 1;
    public static final int INDEX = 0;

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado com a id: " + ID;

    @InjectMocks
    private ChamadoController chamadoController;

    @Mock
    private ChamadoService chamadoService;

    @Mock
    private ModelMapper mapper;

    Chamado chamado;
    ChamadoDTO chamadoDTO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        startChamado();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(chamadoService.findById(anyInt())).thenReturn(chamado);
        when(mapper.map(any(), any())).thenReturn(chamadoDTO);

        ResponseEntity<ChamadoDTO> response = chamadoController.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ChamadoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(DATA_ABERTURA, response.getBody().getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.getBody().getDataFechamento());
        assertEquals(PRIORIDADE, response.getBody().getPrioridade());
        assertEquals(STATUS, response.getBody().getStatus());
        assertEquals(TITULO, response.getBody().getTitulo());
        assertEquals(OBS, response.getBody().getObservacoes());
        assertEquals(TECNICO, response.getBody().getTecnico());
        assertEquals(CLIENTE, response.getBody().getCliente());
    }

    @Test
    void whenFindAllThenReturnLitOfTecnicoDTO() {
        when(chamadoService.findAll()).thenReturn(List.of(chamado));
        when(mapper.map(any(), any())).thenReturn(chamadoDTO);

        ResponseEntity<List<ChamadoDTO>> response = chamadoController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ChamadoDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(DATA_ABERTURA, response.getBody().get(INDEX).getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.getBody().get(INDEX).getDataFechamento());
        assertEquals(PRIORIDADE, response.getBody().get(INDEX).getPrioridade());
        assertEquals(STATUS, response.getBody().get(INDEX).getStatus());
        assertEquals(TITULO, response.getBody().get(INDEX).getTitulo());
        assertEquals(OBS, response.getBody().get(INDEX).getObservacoes());
        assertEquals(TECNICO, response.getBody().get(INDEX).getTecnico());
        assertEquals(CLIENTE, response.getBody().get(INDEX).getCliente());

    }

    @Test
    void whenCreateThenReturnCreated() {
        when(chamadoService.create(any())).thenReturn(chamado);

        ResponseEntity<ChamadoDTO> response = chamadoController.create(chamadoDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(chamadoService.update(chamadoDTO)).thenReturn(chamado);
        when(mapper.map(any(), any())).thenReturn(chamadoDTO);

        ResponseEntity<ChamadoDTO> response = chamadoController.update(ID, chamadoDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ChamadoDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(DATA_ABERTURA, response.getBody().getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.getBody().getDataFechamento());
        assertEquals(PRIORIDADE, response.getBody().getPrioridade());
        assertEquals(STATUS, response.getBody().getStatus());
        assertEquals(TITULO, response.getBody().getTitulo());
        assertEquals(OBS, response.getBody().getObservacoes());
        assertEquals(TECNICO, response.getBody().getTecnico());
        assertEquals(CLIENTE, response.getBody().getCliente());
    }

    private void startChamado() {
        chamado = new Chamado(ID, DATA_ABERTURA, DATA_FECHAMENTO, PRIORIDADE, STATUS, TITULO, OBS, TECNICO, CLIENTE);
        chamadoDTO = new ChamadoDTO(ID, DATA_ABERTURA, DATA_FECHAMENTO, PRIORIDADE, STATUS, TITULO, OBS, TECNICO, CLIENTE, NOME_TECNICO, NOME_CLIENTE);
    }
}