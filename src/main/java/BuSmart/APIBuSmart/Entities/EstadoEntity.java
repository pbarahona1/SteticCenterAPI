package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "TBESTADO")
@Getter @Setter @ToString @EqualsAndHashCode
public class EstadoEntity {

    @Id
    @Column(name ="IDESTADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESTADO")
    @SequenceGenerator(name = "SEQ_ESTADO", sequenceName = "SEQ_ESTADO", allocationSize = 1)
    private Long idEstado;

    @Column(name ="TIPOESTADO")
    private String TipoEstado;

}
