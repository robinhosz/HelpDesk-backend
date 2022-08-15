package com.robson.helpdesk.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robson.helpdesk.enums.Perfil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TecnicoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	protected String nome;

	@CPF
	protected String cpf;
	@Email
	protected String email;
	protected String senha;
	protected Set<Perfil> perfis = new HashSet<>();

	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public TecnicoDTO() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(Perfil.CLIENTE);
	}
}
