package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UNIDADES") // Nombre exacto de la tabla en DB
@Getter @Setter @ToString @EqualsAndHashCode
public class UnidadesEntity {

    @Id
    @Column(name = "IDUNIDAD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UNIDAD")
    @SequenceGenerator(name = "SEQ_UNIDAD", sequenceName = "SEQ_UNIDADES", allocationSize = 1)
    private Long idUnidad;

    @Column(name = "TIPOUNIDAD", nullable = false)
    private String tipoUnidad;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Column(name = "IDESTADO", nullable = false)
    private Integer idEstado;

    @Column(name = "IDRUTA")
    private Integer idRuta;

    @Column(name = "UNIDADES", columnDefinition = "DEFAULT 1")
    private Integer unidades = 1;
}