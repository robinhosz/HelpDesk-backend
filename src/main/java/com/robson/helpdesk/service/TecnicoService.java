package com.robson.helpdesk.service;

import com.robson.helpdesk.dto.TecnicoDTO;
import com.robson.helpdesk.model.Pessoa;
import com.robson.helpdesk.model.Tecnico;
import com.robson.helpdesk.repository.PessoaRepository;
import com.robson.helpdesk.repository.TecnicoRepository;
import com.robson.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.robson.helpdesk.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private ModelMapper mapper;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com a id: " + id));
	}

	public List<Tecnico> findAll() {

		return tecnicoRepository.findAll();
	}

	public Tecnico create(TecnicoDTO obj) {
		obj.setId(null);
		obj.setSenha(encoder.encode(obj.getSenha()));
		findByCpfAndEmail(obj);
		return tecnicoRepository.save(mapper.map(obj, Tecnico.class));
	}

	public Tecnico update(TecnicoDTO obj) {
		findById(obj.getId());
		findByCpfAndEmail(obj);
		return tecnicoRepository.save(mapper.map(obj, Tecnico.class));
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(!obj.getChamados().isEmpty()) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		} 
			tecnicoRepository.deleteById(id);
	}

	
	private void findByCpfAndEmail(TecnicoDTO obj) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(obj.getCpf());
		if(pessoa.isPresent() && pessoa.get().getId() != obj.getId()) {
			throw new DataIntegrityViolationException("CPF Já cadastrado no sistema");
		}
		
		pessoa = pessoaRepository.findByEmail(obj.getEmail()); 
			if(pessoa.isPresent() && pessoa.get().getId() != obj.getId()) {
				throw new DataIntegrityViolationException("E-mail Já cadastrado no sistema");
			}
		}
	}

