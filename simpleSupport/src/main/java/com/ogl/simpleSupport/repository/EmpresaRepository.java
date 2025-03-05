package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
