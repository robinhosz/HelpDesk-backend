package com.robson.helpdesk.service;

import com.robson.helpdesk.dto.ClienteDTO;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.model.Pessoa;
import com.robson.helpdesk.repository.ClienteRepository;
import com.robson.helpdesk.repository.PessoaRepository;
import com.robson.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.robson.helpdesk.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private ModelMapper mapper;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com a id: " + id));
	}

	public List<Cliente> findAll() {

		return clienteRepository.findAll();
	}

	public Cliente create(ClienteDTO obj) {
		obj.setId(null);
		obj.setSenha(encoder.encode(obj.getSenha()));
		findByCpfAndEmail(obj);
		return clienteRepository.save(mapper.map(obj, Cliente.class));
	}

	public Cliente update(ClienteDTO obj) {
		findById(obj.getId());
		findByCpfAndEmail(obj);
		return clienteRepository.save(mapper.map(obj, Cliente.class));
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(!obj.getChamados().isEmpty()) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		} 
		clienteRepository.deleteById(id);
		 
	}

	
	private void findByCpfAndEmail(ClienteDTO obj) {
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

