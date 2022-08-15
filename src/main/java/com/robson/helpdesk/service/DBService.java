package com.robson.helpdesk.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.helpdesk.enums.Perfil;
import com.robson.helpdesk.enums.Prioridade;
import com.robson.helpdesk.enums.Status;
import com.robson.helpdesk.model.Chamado;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.model.Tecnico;
import com.robson.helpdesk.repository.ChamadoRepository;
import com.robson.helpdesk.repository.ClienteRepository;
import com.robson.helpdesk.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "José Robson", "411.001.588-04", "jose@gmail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Tom Mag", "168.555.727-90", "tom@gmail.com", "12345");

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1,
				cli1);

		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}
}
