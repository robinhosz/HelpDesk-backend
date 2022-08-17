package com.robson.helpdesk.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robson.helpdesk.enums.Prioridade;
import com.robson.helpdesk.enums.Status;
import com.robson.helpdesk.model.Cliente;
import com.robson.helpdesk.model.Tecnico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChamadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;

	@NotNull(message = "O campo PRIORIDADE é requerido")
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;

	
	@NotNull(message = "O campo STATUS é requerido")
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotBlank(message = "O campo TITULO é requerido")
	private String titulo;

	@NotBlank(message = "O campo OBSERVAÇÕES é requerido")
	private String observacoes;
	
	private Tecnico tecnico;

	private Cliente cliente;

	private String nomeTecnico;
	private String nomeCliente;
}
