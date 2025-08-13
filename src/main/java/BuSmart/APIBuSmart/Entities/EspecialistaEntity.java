package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ESPECIALISTA")
@Getter @Setter @ToString @EqualsAndHashCode
public class EspecialistaEntity {

    @Id
    @Column(name ="IDESPECIALISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPECIALISTA")
    @SequenceGenerator(name = "SEQ_ESPECIALISTA", sequenceName = "SEQ_ESPECIALISTA", allocationSize = 1)
    private Long idEspecialista;

    @Column(name = "ESPECIALIDAD")
    private String especialidad;

}
