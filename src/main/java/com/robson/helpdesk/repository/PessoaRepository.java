package com.robson.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.helpdesk.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
