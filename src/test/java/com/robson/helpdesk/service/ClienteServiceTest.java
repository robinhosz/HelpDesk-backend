package com.robson.helpdesk.service;

import com.robson.helpdesk.dto.ClienteDTO;
import com.robson.helpdesk.enums.Perfil;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.model.Pessoa;
import com.robson.helpdesk.repository.ClienteRepository;
import com.robson.helpdesk.repository.PessoaRepository;
import com.robson.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.robson.helpdesk.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceTest {

    public static final String NOME = "Robson";
    public static final String EMAIL = "robson@mail.com";
    public static final String CPF = "063.550.699-84";
    public static final String SENHA = "123";
    public static final Set<Perfil> PERFIL = new HashSet<>();
    public static final LocalDate DATA = LocalDate.now();
    public static final Integer ID = 1;
    public static final int INDEX = 0;
    public static final String CLIENTE_POSSUI_ORDENS = "Cliente possui ordens de serviço e não pode ser deletado!";

    public static final String CPF_JA_CADASTRADO_NO_SISTEMA = "CPF Já cadastrado no sistema";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado com a id: " + ID;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private ModelMapper mapper;

    Cliente cliente;
    ClienteDTO clienteDTO;
    Pessoa pessoa;
    Optional<Cliente> optionalCliente;
    Optional<Pessoa> optionalPessoa;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @Test
    void whenFindByIdThenReturnAnTecnicoInstance() {
        when(clienteRepository.findById(anyInt())).thenReturn(optionalCliente);

        Cliente response = clienteService.findById(ID);

        assertNotNull(response);
        assertEquals(Cliente.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(CPF, response.getCpf());
        assertEquals(SENHA, response.getSenha());
        assertEquals(DATA, response.getDataCriacao());
        assertEquals(PERFIL, response.getPerfis());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(clienteRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            clienteService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFinAllThenReturnAnListOfTecnicos() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> response = clienteService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Cliente.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(CPF, response.get(INDEX).getCpf());
        assertEquals(SENHA, response.get(INDEX).getSenha());
        assertEquals(DATA, response.get(INDEX).getDataCriacao());
        assertEquals(PERFIL, response.get(INDEX).getPerfis());

    }

    @Test
    void whenCreateThenReturnSucess() {
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente response = clienteService.create(clienteDTO);

        assertNotNull(response);
        assertEquals(Cliente.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(CPF, response.getCpf());
        assertEquals(SENHA, response.getSenha());
        assertEquals(DATA, response.getDataCriacao());
        assertEquals(PERFIL, response.getPerfis());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(clienteRepository.findById(anyInt())).thenReturn(optionalCliente);
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente responseFind = clienteService.findById(ID);
        Cliente response = clienteService.update(clienteDTO);

        assertNotNull(response);
        assertEquals(Cliente.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(CPF, response.getCpf());
        assertEquals(SENHA, response.getSenha());
        assertEquals(DATA, response.getDataCriacao());
        assertEquals(PERFIL, response.getPerfis());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {
        when(pessoaRepository.findByEmail(anyString())).thenReturn(optionalPessoa);
        when(pessoaRepository.findByCpf(anyString())).thenReturn(optionalPessoa);

        try {
            optionalPessoa.get().setId(2);
            clienteService.create(clienteDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(CPF_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());

        }

    }

    @Test
    void deleteWithSuccess() {
        when(clienteRepository.findById(anyInt())).thenReturn(optionalCliente);
        doNothing().when(clienteRepository).deleteById(anyInt());
        clienteService.delete(ID);
        verify(clienteRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithDataIntegrity() {
        when(clienteRepository.findById(anyInt())).thenThrow(new DataIntegrityViolationException(CLIENTE_POSSUI_ORDENS));
        try {
            clienteService.delete(ID);

        } catch(Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(CLIENTE_POSSUI_ORDENS, ex.getMessage());
        }
    }

    private void startCliente() {
        cliente = new Cliente(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
        clienteDTO = new ClienteDTO(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
        optionalCliente = Optional.of(new Cliente(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA));
        optionalPessoa = Optional.of(new Cliente(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA));
    }
}