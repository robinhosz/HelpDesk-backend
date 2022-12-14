package com.robson.helpdesk.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.robson.helpdesk.enums.Perfil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends Pessoa {

	private static final long serialVersionUID = 1L;

	//Serialization loop refact!
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Chamado> chamados = new ArrayList<>();

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha, Set<Perfil> perfis,
			LocalDate dataCriacao) {
		super(id, nome, cpf, email, senha, perfis, dataCriacao);
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.CLIENTE);

	}

}
