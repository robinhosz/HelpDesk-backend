package com.robson.helpdesk.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.helpdesk.dto.ChamadoDTO;
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

	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}

	public Chamado create(ChamadoDTO obj) {
		obj.setId(null);
		return chamadoRepository.save(mapper.map(obj, Chamado.class));
	}

	public Chamado update(ChamadoDTO obj) {

		findById(obj.getId());
		newChamado(obj);

		return chamadoRepository.save(mapper.map(obj, Chamado.class));
	}

	private void newChamado(ChamadoDTO obj) {

		if (obj.getStatus().ordinal() == 2) {
			obj.setDataFechamento(LocalDate.now());
		}

	}

}
