package com.robson.helpdesk.service;

import com.robson.helpdesk.dto.ChamadoDTO;
import com.robson.helpdesk.enums.Prioridade;
import com.robson.helpdesk.enums.Status;
import com.robson.helpdesk.model.Chamado;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.model.Tecnico;
import com.robson.helpdesk.repository.ChamadoRepository;
import com.robson.helpdesk.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChamadoServiceTest {

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
    private ChamadoService chamadoService;

    @Mock
    private ChamadoRepository chamadoRepository;

    @Mock
    private ModelMapper mapper;

    Chamado chamado;
    ChamadoDTO chamadoDTO;
    Optional<Chamado> optionalChamado;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        startChamado();
    }

    @Test
    void whenFindByIdThenReturnAnChamadoInstance() {
        when(chamadoRepository.findById(anyInt())).thenReturn(optionalChamado);

        Chamado response = chamadoService.findById(ID);

        assertNotNull(response);
        assertEquals(Chamado.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATA_ABERTURA, response.getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.getDataFechamento());
        assertEquals(PRIORIDADE, response.getPrioridade());
        assertEquals(STATUS, response.getStatus());
        assertEquals(TITULO, response.getTitulo());
        assertEquals(OBS, response.getObservacoes());
        assertEquals(TECNICO, response.getTecnico());
        assertEquals(CLIENTE, response.getCliente());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(chamadoRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            chamadoService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFinAllThenReturnAnListOfChamados() {
        when(chamadoRepository.findAll()).thenReturn(List.of(chamado));

        List<Chamado> response = chamadoService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Chamado.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(DATA_ABERTURA, response.get(INDEX).getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.get(INDEX).getDataFechamento());
        assertEquals(PRIORIDADE, response.get(INDEX).getPrioridade());
        assertEquals(STATUS, response.get(INDEX).getStatus());
        assertEquals(TITULO, response.get(INDEX).getTitulo());
        assertEquals(OBS, response.get(INDEX).getObservacoes());
        assertEquals(TECNICO, response.get(INDEX).getTecnico());
        assertEquals(CLIENTE, response.get(INDEX).getCliente());

    }

    @Test
    void whenCreateThenReturnSucess() {
        when(chamadoRepository.save(any())).thenReturn(chamado);

        Chamado response = chamadoService.create(chamadoDTO);

        assertNotNull(response);
        assertEquals(Chamado.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATA_ABERTURA, response.getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.getDataFechamento());
        assertEquals(PRIORIDADE, response.getPrioridade());
        assertEquals(STATUS, response.getStatus());
        assertEquals(TITULO, response.getTitulo());
        assertEquals(OBS, response.getObservacoes());
        assertEquals(TECNICO, response.getTecnico());
        assertEquals(CLIENTE, response.getCliente());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(chamadoRepository.findById(anyInt())).thenReturn(optionalChamado);
        when(chamadoRepository.save(any())).thenReturn(chamado);

        Chamado responseFind = chamadoService.findById(ID);
        Chamado response = chamadoService.update(chamadoDTO);

        assertNotNull(response);
        assertEquals(Chamado.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(DATA_ABERTURA, response.getDataAbertura());
        assertEquals(DATA_FECHAMENTO, response.getDataFechamento());
        assertEquals(PRIORIDADE, response.getPrioridade());
        assertEquals(STATUS, response.getStatus());
        assertEquals(TITULO, response.getTitulo());
        assertEquals(OBS, response.getObservacoes());
        assertEquals(TECNICO, response.getTecnico());
        assertEquals(CLIENTE, response.getCliente());
    }

    private void startChamado() {
        chamado = new Chamado(ID, DATA_ABERTURA, DATA_FECHAMENTO, PRIORIDADE, STATUS, TITULO, OBS, TECNICO, CLIENTE);
        chamadoDTO = new ChamadoDTO(ID, DATA_ABERTURA, DATA_FECHAMENTO, PRIORIDADE, STATUS, TITULO, OBS, TECNICO, CLIENTE, NOME_TECNICO, NOME_CLIENTE);
        optionalChamado = Optional.of(new Chamado(ID, DATA_ABERTURA, DATA_FECHAMENTO, PRIORIDADE, STATUS, TITULO, OBS, TECNICO, CLIENTE));
    }
}