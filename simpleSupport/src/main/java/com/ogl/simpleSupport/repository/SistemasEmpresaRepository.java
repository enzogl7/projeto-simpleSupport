package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.model.SistemasEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SistemasEmpresaRepository extends JpaRepository<SistemasEmpresa, Long> {
    List<SistemasEmpresa> findByEmpresa(Empresa empresa);
}
