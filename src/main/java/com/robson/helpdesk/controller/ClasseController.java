package com.robson.helpdesk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/testes")
public class ClasseController {
	
	@GetMapping
	public String helloWorld() {
		String valor = "Hello world com spring";
		return valor;
	}

}
