package com.Valverde.PNL.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import com.Valverde.PNL.entity.Denuncia;

public interface DenunciaService {
    public List<Denuncia> findAll(Pageable page);
    public List<Denuncia> findAll();
    public List<Denuncia> findByNombre(String nombre, Pageable page);
    public Denuncia findById(int id);
    public Denuncia save(Denuncia denuncia);
    public void delete(int id);
}
