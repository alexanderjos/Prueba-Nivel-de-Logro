package com.Valverde.PNL.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.Valverde.PNL.entity.Denuncia;
import com.Valverde.PNL.exception.GeneralException;
import com.Valverde.PNL.exception.NoDataFoundException;
import com.Valverde.PNL.exception.ValidateException;
import com.Valverde.PNL.repository.DenunciaRepository;
import com.Valverde.PNL.service.DenunciaService;
import com.Valverde.PNL.validator.DenunciaValidator;


@Service
public class DenunciaServiceImpl  implements DenunciaService{

    @Autowired
    private DenunciaRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Denuncia> findAll(Pageable page) {
        try {
            List<Denuncia> registros = repository.findAll(page).toList();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidorr");
        }
    }

    @Override
    public List<Denuncia> findAll() {
        try {
            List<Denuncia> registros = repository.findAll();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidorr");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Denuncia> findByNombre(String nombre, Pageable page) {
        try {
            List<Denuncia> registros = repository.findByTituloContaining(nombre, page);
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    public Denuncia findById(int id) {
        try {
            Denuncia registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public Denuncia save(Denuncia denuncia) {
        try {
            DenunciaValidator.save(denuncia);

            if(denuncia.getId() == 0) {
                Denuncia nuevo = repository.save(denuncia);
                return nuevo;
            }

            Denuncia registro = repository.findById(denuncia.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            registro.setTitulo(denuncia.getTitulo());
            registro.setDescripcion(denuncia.getDescripcion());
            registro.setUbicacion(denuncia.getUbicacion());
            registro.setEstado(denuncia.getEstado());
            registro.setCiudadano(denuncia.getCiudadano());
            registro.setTelefono(denuncia.getTelefono());
            registro.setFecha(denuncia.getFecha());
            repository.save(registro);

            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            Denuncia registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro como ese id"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

}
