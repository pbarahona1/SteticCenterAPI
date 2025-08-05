package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBTIPOENCARGADO")
@Getter @Setter @ToString @EqualsAndHashCode
public class TipoEncargadoEntity {

    @Id
    @Column(name ="IDTIPOENCARGADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPOENCARGADO")
    @SequenceGenerator(name = "SEQ_TIPOENCARGADO", sequenceName = "SEQ_TIPOENCARGADO", allocationSize = 1)
    private Long IdTipoEncargado;

    @Column(name = "TIPOFAMILIAR")
    private String TipoFamiliar;

}
