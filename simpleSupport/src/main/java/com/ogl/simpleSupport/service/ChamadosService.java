package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.repository.ChamadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChamadosService {
    @Autowired
    private ChamadosRepository chamadosRepository;
}
