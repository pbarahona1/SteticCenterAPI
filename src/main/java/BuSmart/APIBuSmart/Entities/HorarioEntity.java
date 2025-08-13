package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "HORARIO")
@Getter @Setter @ToString @EqualsAndHashCode
public class HorarioEntity {

    @Id
    @Column(name ="IDHORARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HORARIO")
    @SequenceGenerator(name = "SEQ_HORARIO", sequenceName = "SEQ_HORARIO", allocationSize = 1)
    private Long idHorario;

    @Column(name ="DESCRIPCION")
    private String descripcion;

}
