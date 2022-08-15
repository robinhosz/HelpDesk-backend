package com.robson.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robson.helpdesk.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
