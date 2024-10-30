    package com.Valverde.PNL.entity;
    import java.util.Date;

    import org.springframework.data.annotation.CreatedDate;
    import org.springframework.data.annotation.LastModifiedDate;
    import org.springframework.data.jpa.domain.support.AuditingEntityListener;

    import jakarta.persistence.*;
    import lombok.*;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Builder
    @Table(name = "denuncias")
    @EntityListeners(AuditingEntityListener.class)
    public class Denuncia {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(length = 100, nullable = false)
        private String titulo;

        @Column(name = "descripcion", length = 255)
        private String descripcion;

        @Column(name = "ubicacion", length = 150)
        private String ubicacion;

        @Column(name="estado", length = 20)
        private String estado;

        @Column(name="ciudadano", length = 100)
        private String ciudadano;
        
        @Column(name = "telefono_ciudadano", precision = 15)
        private String telefono;

        @Column(name = "fecha_registro")
        @Temporal(TemporalType.TIMESTAMP)
        private Date fecha;

        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        private Date createdAt;

        @Column(name = "updated_at")
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        private Date updatedAt;
    }
