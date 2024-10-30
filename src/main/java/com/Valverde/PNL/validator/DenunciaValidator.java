package com.Valverde.PNL.validator;

import com.Valverde.PNL.entity.Denuncia;
import com.Valverde.PNL.exception.ValidateException;

public class DenunciaValidator {
    public static void save(Denuncia denuncia) {
        if (denuncia.getTitulo() == null || denuncia.getTitulo().isEmpty()) {
            throw new ValidateException("El titulo no puede ser vacio");
            
        }
        if (denuncia.getDescripcion() == null || denuncia.getDescripcion().isEmpty()) {
            throw new ValidateException("La descripcion no puede ser vacia");
        }
        if (denuncia.getUbicacion() == null || denuncia.getUbicacion().isEmpty()) {
            throw new ValidateException("La ubicacion no puede ser vacia");
        }
        if (denuncia.getEstado() == null || denuncia.getEstado().isEmpty()) {
            throw new ValidateException("El estado no puede ser vacio");
        }
        if (denuncia.getCiudadano() == null || denuncia.getCiudadano().isEmpty()) {
            throw new ValidateException("El ciudadano no puede ser vacio");
        }
        if (denuncia.getTelefono() == null || denuncia.getTelefono().isEmpty()) {
            throw new ValidateException("El telefono del ciudadano no puede ser vacio");
        }
        if (denuncia.getFecha() == null) {
            throw new ValidateException("La fecha de registro no puede ser vacia");
        }
        if (denuncia.getTitulo().length() > 100) {
            throw new ValidateException("El titulo no puede tener mas de 100 caracteres");
        }
        if (denuncia.getDescripcion().length() > 255) {
            throw new ValidateException("La descripcion no puede tener mas de 255 caracteres");
        }
        if (denuncia.getUbicacion().length() > 150) {
            throw new ValidateException("La ubicacion no puede tener mas de 150 caracteres");
        }
        if (denuncia.getEstado().length() > 20) {
            throw new ValidateException("El estado no puede tener mas de 20 caracteres");
        }
        if (denuncia.getCiudadano().length() > 100) {
            throw new ValidateException("El ciudadano no puede tener mas de 100 caracteres");
        }
        if (denuncia.getTelefono().length() > 15) {
            throw new ValidateException("El telefono del ciudadano no puede tener mas de 15 caracteres");
        }
    }
}
