package com.robson.helpdesk.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

	@NotBlank(message = "O campo NOME é requerido")
	protected String nome;

	@CPF
	protected String cpf;
	@Email
	protected String email;

	@NotBlank(message = "O campo SENHA é requerido")
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
