package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SERVICIOS")
@Getter @Setter
@EqualsAndHashCode @ToString
public class EntitieServicios {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Servicios")
    @SequenceGenerator(name = "seq_Servicios", sequenceName = "seq_Servicios", allocationSize = 1)
    @Column(name = "idServicio")
    private Long idServicio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Long precio;

    @Column(name = "duracion_min")
    private Long duracion_min;
}
