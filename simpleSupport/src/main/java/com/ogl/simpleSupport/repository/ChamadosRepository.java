package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.Chamados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadosRepository extends JpaRepository<Chamados, Long> {
}
