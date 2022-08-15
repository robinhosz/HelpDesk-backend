package com.robson.helpdesk.controller.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationErro extends StandardError implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName, message));
	}

	public ValidationErro(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
		// TODO Auto-generated constructor stub
	}

}