package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa save(String nome, String cnpj, String email, String razaoSocial, String emailResponsavel) {
        Empresa empresa = new Empresa();
        empresa.setNome(nome);
        empresa.setCnpj(cnpj);
        empresa.setEmailEmpresa(email);
        empresa.setRazaoSocial(razaoSocial);
        empresa.setEmailResponsavel(emailResponsavel);
        empresaRepository.save(empresa);
        return empresa;
    }

    public Empresa findById(long id) {
        return empresaRepository.findById(id).get();
    }

    public void salvarEdicao(Empresa empresa) {
        empresaRepository.save(empresa);
    }
}
