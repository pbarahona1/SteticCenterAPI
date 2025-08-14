package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "UsuarioServicios")
public class EntitieUsuarioServicios {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_UsuarioServicios")
    @SequenceGenerator(name = "seq_UsuarioServicios", sequenceName = "seq_UsuarioServicios", allocationSize = 1)
    @Column(name = "idUsuarioServicio")
    private long idUsuarioServicio;

    @Column(name = "idUsuario")
    private long idUsuario;

    @Column(name = "idServicio")
    private long idServicio;


}
