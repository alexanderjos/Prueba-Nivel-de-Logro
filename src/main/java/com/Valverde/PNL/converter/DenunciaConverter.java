package com.Valverde.PNL.converter;
import org.springframework.stereotype.Component;

import com.Valverde.PNL.dto.DenunciaDto;
import com.Valverde.PNL.entity.Denuncia;


@Component
public class DenunciaConverter extends AbstractConverter<Denuncia,DenunciaDto> {
    @Override
    public DenunciaDto fromEntity(Denuncia entity) {
        if (entity == null) return null;

        return DenunciaDto.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .descripcion(entity.getDescripcion())
                .ubicacion(entity.getUbicacion())
                .estado(entity.getEstado())
                .ciudadano(entity.getCiudadano())
                .telefono(entity.getTelefono())
                .fecha(entity.getFecha())
                .build();
    }

    @Override
    public Denuncia fromDTO(DenunciaDto dto) {
        if (dto == null) return null;

        return Denuncia.builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .ubicacion(dto.getUbicacion())
                .estado(dto.getEstado())
                .ciudadano(dto.getCiudadano())
                .telefono(dto.getTelefono())
                .fecha(dto.getFecha())
                .build();
    }

}
