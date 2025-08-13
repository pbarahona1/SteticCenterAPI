package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "DETALLEUSUARIO")
@Getter @Setter @ToString @EqualsAndHashCode
public class DetalleUsuarioEntity {

    @Id
    @Column(name ="IDDETALLEUSUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALLEUSUARIO")
    @SequenceGenerator(name = "SEQ_DETALLEUSUARIO", sequenceName = "SEQ_DETALLEUSUARIO", allocationSize = 1)
    private Long idDetalleUsuario;

    @Column(name ="IDUSUARIO")
    private int idUsuario;

    @Column(name ="IDESPECIALISTA")
    private int idEspecialista;

}
