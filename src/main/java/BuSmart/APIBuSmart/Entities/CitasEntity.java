package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "CITAS")
@Getter @Setter @ToString @EqualsAndHashCode
public class CitasEntity {

    @Id
    @Column(name = "IDCITA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITAS")
    @SequenceGenerator(name = "SEQ_CITAS", sequenceName = "SEQ_CITAS", allocationSize = 1)
    private long idCita;

    @Column(name = "IDUSUARIO")
    private int idUsuario;

    @Column(name = "IDCLIENTE")
    private int idCliente;

    @Column(name = "IDHORARIO")
    private int idHorario;

    @Column(name = "FECHA_CITA")
    private Date fecha_cita;

    @Column(name = "ESTADO")
    private String estado;

}
