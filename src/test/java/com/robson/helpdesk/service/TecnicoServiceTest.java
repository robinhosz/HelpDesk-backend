package com.robson.helpdesk.service;

import com.robson.helpdesk.dto.TecnicoDTO;
import com.robson.helpdesk.enums.Perfil;
import com.robson.helpdesk.model.Pessoa;
import com.robson.helpdesk.model.Tecnico;
import com.robson.helpdesk.repository.PessoaRepository;
import com.robson.helpdesk.repository.TecnicoRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest
class TecnicoServiceTest {

	public static final String NOME = "Valdir";
	public static final String EMAIL = "valdir@mail.com";
	public static final String CPF = "063.550.699-84";
	public static final String SENHA = "123";
	public static final Set<Perfil> PERFIL = new HashSet<>();
	public static final LocalDate DATA = LocalDate.now();
	public static final Integer ID = 1;
	public static final int INDEX = 0;
	public static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail Já cadastrado no sistema";

	public static final String CPF_JA_CADASTRADO_NO_SISTEMA = "CPF Já cadastrado no sistema";
	public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado com a id: " + ID;

	@InjectMocks
	private TecnicoService tecnicoService;

	@Mock
	private TecnicoRepository tecnicoRepository;

	@Mock
	private PessoaRepository pessoaRepository;

	@Mock
	private BCryptPasswordEncoder encoder;

	@Mock
	private ModelMapper mapper;

	Tecnico tecnico;
	TecnicoDTO tecnicoDTO;
	Pessoa pessoa;
	Optional<Tecnico> optionalTecnico;
	Optional<Pessoa> optionalPessoa;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startTecnico();
	}

	@Test
	void whenFindByIdThenReturnAnTecnicoInstance() {
		when(tecnicoRepository.findById(anyInt())).thenReturn(optionalTecnico);

		Tecnico response = tecnicoService.findById(ID);

		assertNotNull(response);
		assertEquals(Tecnico.class, response.getClass());
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
		when(tecnicoRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

		try {
			tecnicoService.findById(ID);
		} catch (Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
		}
	}

	@Test
	void whenFinAllThenReturnAnListOfTecnicos() {
		when(tecnicoRepository.findAll()).thenReturn(List.of(tecnico));

		List<Tecnico> response = tecnicoService.findAll();

		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(Tecnico.class, response.get(INDEX).getClass());

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
		when(tecnicoRepository.save(any())).thenReturn(tecnico);

		Tecnico response = tecnicoService.create(tecnicoDTO);

		assertNotNull(response);
		assertEquals(Tecnico.class, response.getClass());
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
		when(tecnicoRepository.findById(anyInt())).thenReturn(optionalTecnico);
		when(tecnicoRepository.save(any())).thenReturn(tecnico);

		Tecnico responseFind = tecnicoService.findById(ID);
		Tecnico response = tecnicoService.update(tecnicoDTO);

		assertNotNull(response);
		assertEquals(Tecnico.class, response.getClass());
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
			tecnicoService.create(tecnicoDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegrityViolationException.class, ex.getClass());
			assertEquals(CPF_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());

		}

	}

	private void startTecnico() {
		tecnico = new Tecnico(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
		tecnicoDTO = new TecnicoDTO(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA);
		optionalTecnico = Optional.of(new Tecnico(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA));
		optionalPessoa = Optional.of(new Tecnico(ID, NOME, CPF, EMAIL, SENHA, PERFIL, DATA));
	}

}
