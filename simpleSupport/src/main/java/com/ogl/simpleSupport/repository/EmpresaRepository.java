package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByEmailResponsavel(String emailResponsavel);
}
