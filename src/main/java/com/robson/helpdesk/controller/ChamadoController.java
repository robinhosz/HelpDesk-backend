package com.robson.helpdesk.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.robson.helpdesk.dto.ChamadoDTO;
import com.robson.helpdesk.service.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoController {

	@Autowired
	private ChamadoService chamadoService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok().body(mapper.map(chamadoService.findById(id), ChamadoDTO.class));

	}

	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {

		List<ChamadoDTO> listDTO = chamadoService.findAll().stream().map(x -> mapper.map(x, ChamadoDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

}
