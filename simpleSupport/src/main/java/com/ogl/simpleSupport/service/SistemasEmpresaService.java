package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.repository.SistemasEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SistemasEmpresaService {
    @Autowired
    private SistemasEmpresaRepository sistemasEmpresaRepository;
}
