package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.SistemasEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemasEmpresaRepository extends JpaRepository<SistemasEmpresa, Long> {
}
