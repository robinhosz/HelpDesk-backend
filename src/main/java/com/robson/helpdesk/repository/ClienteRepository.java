package com.robson.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.helpdesk.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
