package BuSmart.APIBuSmart.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CLIENTE")
@Getter @Setter @ToString @EqualsAndHashCode
public class ClienteEntity {

    @Id
    @Column(name = "idCliente")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Cliente")
    @SequenceGenerator(name = "seq_Cliente", sequenceName = "seq_Cliente", allocationSize = 1)
    private Integer idCliente;  // Cambiado de int a Integer

    @Column(name = "nombreCompleto", nullable = false)
    private String nombreComplete;

    @Column(name = "direccion", nullable = false)
    private String direction;

    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "contrasenaCliente")
    private String contrasenaCliente;
}