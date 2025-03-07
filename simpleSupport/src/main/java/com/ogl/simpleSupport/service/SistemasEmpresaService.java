package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.model.SistemasEmpresa;
import com.ogl.simpleSupport.repository.SistemasEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SistemasEmpresaService {
    @Autowired
    private SistemasEmpresaRepository sistemasEmpresaRepository;

    @Autowired
    private EmpresaService empresaService;

    public List<SistemasEmpresa> findByEmpresa(Empresa empresa) {
        return sistemasEmpresaRepository.findByEmpresa(empresa);
    }

    public void salvar(SistemasEmpresa empresa) {
        sistemasEmpresaRepository.save(empresa);
    }

    public SistemasEmpresa findById(Long id) {
        return sistemasEmpresaRepository.findById(id).get();
    }

    public void removerSistema(SistemasEmpresa sistema) {
        sistemasEmpresaRepository.delete(sistema);
    }
}
