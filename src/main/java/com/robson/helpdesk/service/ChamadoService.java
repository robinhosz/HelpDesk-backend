package com.robson.helpdesk.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.helpdesk.model.Chamado;
import com.robson.helpdesk.repository.ChamadoRepository;
import com.robson.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com a id: " + id));
	}
}
