package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa save(Empresa empresa) {
        empresaRepository.save(empresa);
        return empresa;
    }

    public Empresa findById(long id) {
        return empresaRepository.findById(id).get();
    }

    public void salvarEdicao(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public List<Empresa> findByEmailResponsavel(String emailResponsavel) {
        return empresaRepository.findByEmailResponsavel(emailResponsavel);
    }
}
