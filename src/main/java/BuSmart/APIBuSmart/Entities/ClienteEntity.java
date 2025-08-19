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
    @Column(name = "IDCLIENTE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Cliente")
    @SequenceGenerator(name = "seq_Cliente", sequenceName = "seq_Cliente", allocationSize = 1)
    private int idCliente;

    @Column(name = "NOMBRECOMPLETO", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "DIRECCION", nullable = false, length = 100)
    private String direccion;

    @Column(name = "CORREO", length = 100, unique = true)
    private String correo;

    @Column(name = "CONTRASENACLIENTE", length = 256)
    private String contrasenaCliente;
}