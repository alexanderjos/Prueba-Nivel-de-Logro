package com.Valverde.PNL.dto;

import java.util.Date;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DenunciaDto {
    private int id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String estado;
    private String ciudadano;
    private String telefono;
    private Date fecha;

}
