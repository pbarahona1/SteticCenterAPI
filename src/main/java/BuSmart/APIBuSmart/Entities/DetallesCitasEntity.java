package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DETALLESCITA")
@Getter @Setter @ToString @EqualsAndHashCode
public class DetallesCitasEntity {

    @Id
    @Column(name = "IDDETALLECITA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLESCITA")
    @SequenceGenerator(name = "SEQ_DETALLESCITA", sequenceName = "SEQ_DETALLESCITA", allocationSize = 1)
    private long idDetalleCita;

    @Column(name = "IDCITA")
    private int idCita;

    @Column(name = "OBSERVACIONES")
    private String observaciones;
}
